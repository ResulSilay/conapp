package com.adapter.multiple.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.adapter.multiple.R
import com.adapter.multiple.data.api.model.ResultModel
import com.adapter.multiple.databinding.ItemStoreContentAudioBookBinding
import com.adapter.multiple.databinding.ItemStoreContentTrackBinding
import com.adapter.multiple.ui.search.adapter.StoreContentAdapter.BookType.AUDIO_BOOK_TYPE
import com.adapter.multiple.ui.search.adapter.StoreContentAdapter.BookType.TRACK_TYPE
import com.adapter.multiple.ui.search.adapter.StoreContentAdapter.BookViewType.AUDIO_BOOK
import com.adapter.multiple.ui.search.adapter.StoreContentAdapter.BookViewType.TRACK
import com.adapter.multiple.util.*
import com.squareup.picasso.Picasso

class StoreContentAdapter(storeContents: List<ResultModel>, private val storeContentAdapterCallBack: StoreContentAdapterCallBack) : RecyclerView.Adapter<StoreContentAdapter.ViewHolder>() {

    private var storeContentList: List<ResultModel> = ArrayList()
    private var layoutType: Int = TRACK.value

    init {
        this.storeContentList = storeContents
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (layoutType) {
            TRACK.value -> {
                val binding: ItemStoreContentTrackBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_store_content_track, parent, false)
                ViewHolder(binding)
            }
            AUDIO_BOOK.value -> {
                val binding: ItemStoreContentAudioBookBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_store_content_audio_book, parent, false)
                ViewHolder(binding)
            }
            else -> {
                val binding: ItemStoreContentTrackBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_store_content_track, parent, false)
                ViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resultModel = getItem(position)

        when (resultModel.wrapperType) {
            TRACK_TYPE.value -> {
                holder.itemStoreContentTrackBinding.trackModel = resultModel
                holder.itemStoreContentTrackBinding.mainCL.tint(position)

                holder.itemStoreContentTrackBinding.playEFAB.setOnClickListener {
                    storeContentAdapterCallBack.onItemTrackPlayClick(resultModel)
                }

                Picasso.get().load(resultModel.artworkUrl100.sizePhoto(500)).into(holder.itemStoreContentTrackBinding.coverPhotoIV)

                if (position == 0) {
                    holder.itemStoreContentTrackBinding.contentCL.padding(top = 100.px)
                } else {
                    holder.itemStoreContentTrackBinding.contentCL.padding(top = 36.px)
                }
            }

            AUDIO_BOOK_TYPE.value -> {
                holder.itemStoreContentAudioBookBinding.audioBookModel = resultModel
                holder.itemStoreContentAudioBookBinding.mainCL.tint(position)

                holder.itemStoreContentAudioBookBinding.rootCL.setOnClickListener {
                    storeContentAdapterCallBack.onItemAudioBookClick(resultModel)
                }

                holder.itemStoreContentAudioBookBinding.playFAB.setOnClickListener {
                    storeContentAdapterCallBack.onItemAudioBookPlayClick(resultModel)
                }

                Picasso.get().load(resultModel.artworkUrl100.sizePhoto(500)).into(holder.itemStoreContentAudioBookBinding.coverPhotoIV)
                Picasso.get().load(resultModel.artworkUrl100.sizePhoto(200)).into(holder.itemStoreContentAudioBookBinding.coverPhotoCardIV)

                if (position == 0) {
                    holder.itemStoreContentAudioBookBinding.mainCL.padding(top = 100.px)
                } else {
                    holder.itemStoreContentAudioBookBinding.mainCL.padding(top = 44.px)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        when (getItem(position).wrapperType) {
            TRACK_TYPE.value -> {
                layoutType = TRACK.value
            }
            AUDIO_BOOK_TYPE.value -> {
                layoutType = AUDIO_BOOK.value
            }
        }

        return layoutType
    }

    private fun getItem(position: Int): ResultModel {
        return storeContentList[position]
    }

    override fun getItemCount(): Int {
        return storeContentList.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder {
        lateinit var itemStoreContentTrackBinding: ItemStoreContentTrackBinding
        lateinit var itemStoreContentAudioBookBinding: ItemStoreContentAudioBookBinding

        constructor(itemView: ItemStoreContentTrackBinding) : super(itemView.root) {
            itemStoreContentTrackBinding = itemView
        }

        constructor(itemView: ItemStoreContentAudioBookBinding) : super(itemView.root) {
            itemStoreContentAudioBookBinding = itemView
        }
    }

    interface StoreContentAdapterCallBack {
        fun onItemTrackClick(item: ResultModel)
        fun onItemTrackPlayClick(item: ResultModel)
        fun onItemAudioBookClick(item: ResultModel)
        fun onItemAudioBookPlayClick(item: ResultModel)
    }

    enum class BookViewType(val value: Int) {
        TRACK(0),
        AUDIO_BOOK(1)
    }

    enum class BookType(val value: String) {
        TRACK_TYPE("track"),
        AUDIO_BOOK_TYPE("audiobook")
    }
}
