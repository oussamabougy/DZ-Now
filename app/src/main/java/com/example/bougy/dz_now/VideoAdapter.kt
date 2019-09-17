package com.example.sayaradz_mobile.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;



class VideoAdapter(val videoIds: Array<String>, val lifecycle: Lifecycle) : RecyclerView.Adapter<VideoAdapter.ViewHolder>(){


    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): VideoAdapter.ViewHolder {
        val youTubePlayerView = LayoutInflater.from(parent.context).inflate(
            com.example.bougy.dz_now.R.layout.video_item,
            parent,
            false
        ) as YouTubePlayerView
        lifecycle.addObserver(youTubePlayerView)

        return ViewHolder(youTubePlayerView)
    }

    override fun onBindViewHolder(@NonNull viewHolder: ViewHolder, position: Int) {
        viewHolder.cueVideo(videoIds[position])

    }

    override fun getItemCount(): Int {
        return videoIds.size
    }

    class ViewHolder(private val youTubePlayerView: YouTubePlayerView) : RecyclerView.ViewHolder(youTubePlayerView) {
        private var youTubePlayer: YouTubePlayer? = null
        private var currentVideoId: String? = null

        init {

            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(@NonNull initializedYouTubePlayer: YouTubePlayer) {
                    youTubePlayer = initializedYouTubePlayer
                    youTubePlayer!!.cueVideo(currentVideoId!!, 0.toFloat())
                }
            })
        }

        fun cueVideo(videoId: String) {
            currentVideoId = videoId

            if (youTubePlayer == null)
                return

            youTubePlayer!!.cueVideo(videoId, 0.toFloat())
        }
    }
}