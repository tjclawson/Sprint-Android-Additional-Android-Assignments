package com.example.mediaservices

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import androidx.annotation.RawRes
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import kotlinx.android.synthetic.main.activity_exo_player.*

class ExoPlayer : AppCompatActivity() {

    lateinit var exoPlayer: SimpleExoPlayer
    val handler = Handler()
    val delay = 1000
    var videoDuration: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exo_player)

        createPlayer()
        setupPlayFile()
        playback_control.player = exoPlayer
        player_view.player = exoPlayer
        playback_control.showTimeoutMs = 0
        playback_control.setRewindIncrementMs(5000)
        playback_control.setFastForwardIncrementMs(5000)
    }

    override fun onStop() {
        super.onStop()
        exoPlayer.stop()
    }

    fun setupPlayFile() {
        exoPlayer.prepare(createRawMediaSource(R.raw.live_views_of_starman))
    }

    fun createPlayer() {
        val trackSelector = DefaultTrackSelector()
        val loadControl = DefaultLoadControl()
        val renderersFactory = DefaultRenderersFactory(this)

        exoPlayer = ExoPlayerFactory.newSimpleInstance(this, renderersFactory, trackSelector, loadControl)
        exoPlayer.videoScalingMode = C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING
    }

    fun createRawMediaSource(@RawRes rawId: Int): MediaSource {
        val rawResourceDataSource = RawResourceDataSource(this)
        val dataSpec = DataSpec(RawResourceDataSource.buildRawResourceUri(rawId))
        rawResourceDataSource.open(dataSpec)
        return ExtractorMediaSource.Factory(DataSource.Factory {
            rawResourceDataSource
        }).createMediaSource(rawResourceDataSource.uri)
    }
}


