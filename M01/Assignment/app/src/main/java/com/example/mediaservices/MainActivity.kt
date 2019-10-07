package com.example.mediaservices

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_pause_play.isEnabled = false
        playPauseButtonInit()
        Log.i("BIGBRAIN", "${R.raw.live_views_of_starman}")

    }

    private fun playPauseButtonInit() {

        button_pause_play.setOnClickListener {
            if (vv_main.isPlaying) {
                vv_main.pause()
                button_pause_play.text = getString(R.string.play)
            } else {
                vv_main.start()
                button_pause_play.text = getString(R.string.pause)
            }
        }
    }

    override fun onStart() {
        super.onStart()

        //setting video URI
        vv_main.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.live_views_of_starman))

        //setting on prepared listener
        vv_main.setOnPreparedListener {
            button_pause_play.isEnabled = true
            it.let {
                seekbar.max = it.duration
            }
        }
    }
}
