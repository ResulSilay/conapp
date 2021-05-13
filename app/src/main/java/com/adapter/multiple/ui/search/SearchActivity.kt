package com.adapter.multiple.ui.search

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adapter.multiple.R
import com.adapter.multiple.data.api.model.ResultModel
import com.adapter.multiple.data.api.repository.StoreRepository
import com.adapter.multiple.data.other.model.PlayerModel
import com.adapter.multiple.databinding.ActivitySearchBinding
import com.adapter.multiple.ui.search.adapter.StoreContentAdapter
import com.adapter.multiple.ui.search.adapter.StoreContentAdapter.StoreContentAdapterCallBack
import com.adapter.multiple.ui.search.bottomSheet.PlayerBottomSheet.Companion.showPlayerBottomSheet

class SearchActivity : AppCompatActivity() {

    ///API
    //https://itunes.apple.com/search?term=book
    //MEDIA
    //https://audio-ssl.itunes.apple.com/itunes-assets/Music/c6/be/cf/mzm.hmubyebh.aac.p.m4a
    ///

    private lateinit var binding: ActivitySearchBinding
    private lateinit var viewModel: SearchActivityViewModel
    private lateinit var storeRepository: StoreRepository
    private lateinit var storeContentAdapter: StoreContentAdapter

    private var storeContentList: MutableList<ResultModel> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        storeRepository = StoreRepository()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        viewModel = ViewModelProvider(this, SearchViewModelFactory(storeRepository = storeRepository)).get(SearchActivityViewModel::class.java)
        binding.searchVM = viewModel

        initRecycle()
        initObserver()
        initSearch()
    }

    override fun onResume() {
        super.onResume()
        binding.searchSV.clearFocus()
    }

    private fun initRecycle() {
        storeContentAdapter = StoreContentAdapter(storeContentList, object : StoreContentAdapterCallBack {
            override fun onItemTrackClick(item: ResultModel) {

            }

            override fun onItemTrackPlayClick(item: ResultModel) {
                showPlayerBottomSheet(fragmentManager = supportFragmentManager, playerModel = PlayerModel().apply {
                    this.url = item.previewUrl
                })
            }

            override fun onItemAudioBookClick(item: ResultModel) {

            }

            override fun onItemAudioBookPlayClick(item: ResultModel) {
                showPlayerBottomSheet(fragmentManager = supportFragmentManager, playerModel = PlayerModel().apply {
                    this.url = item.previewUrl
                })
            }
        })

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.resultRV.layoutManager = linearLayoutManager
        binding.resultRV.adapter = storeContentAdapter
    }

    private fun initObserver() {
        viewModel.getContent(searchText = "book")

        viewModel.storeContentLiveData.observe(this, Observer {
            storeContentList.clear()
            storeContentList.addAll(it.results)
            storeContentAdapter.notifyDataSetChanged()
        })
    }

    private fun initSearch() {
        binding.searchSV.onActionViewExpanded()
        binding.searchSV.isFocusable = false

        binding.searchSV.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                viewModel.getContent(searchText = query)

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }
}