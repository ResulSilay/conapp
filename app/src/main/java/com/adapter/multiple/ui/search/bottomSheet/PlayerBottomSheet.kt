package com.adapter.multiple.ui.search.bottomSheet

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.adapter.multiple.R
import com.adapter.multiple.data.other.model.PlayerModel
import com.adapter.multiple.databinding.BottomSheetPlayerBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class PlayerBottomSheet : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetPlayerBinding
    private var playerModel: PlayerModel? = null
    private val mediaPlayer = MediaPlayer()

    override fun getTheme(): Int {
        return R.style.AppBottomSheetDialogTheme
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        playerModel = arguments?.get("playerModel") as PlayerModel?
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_player, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playerModel?.let {
            initMediaPlayer(it.url.toString())?.start()

            val audioSessionId: Int = mediaPlayer.audioSessionId
            if (audioSessionId != -1) binding.visualizer.setAudioSessionId(audioSessionId)
        }

        binding.playOrPauseBTN.setOnClickListener {
            if (mediaPlayer.isPlaying) {
                binding.playOrPauseBTN.setImageResource(R.drawable.ic_play)
                mediaPlayer.pause()
            } else {
                binding.playOrPauseBTN.setImageResource(R.drawable.ic_stop)
                mediaPlayer.start()
            }
        }
    }

    private fun initMediaPlayer(mediaFile: String): MediaPlayer? {
        mediaPlayer.setDataSource(mediaFile)
        mediaPlayer.prepare()
        return mediaPlayer
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
        binding.visualizer.release()
    }

    companion object {
        fun showPlayerBottomSheet(fragmentManager: FragmentManager, playerModel: PlayerModel) {
            val playerBottomSheet = PlayerBottomSheet()

            playerBottomSheet.isCancelable = true
            playerBottomSheet.arguments = Bundle().apply {
                this.putSerializable("playerModel", playerModel)
            }

            if (!playerBottomSheet.isAdded) {
                playerBottomSheet.show(fragmentManager, playerBottomSheet::class.java.name)
            }
        }
    }

}