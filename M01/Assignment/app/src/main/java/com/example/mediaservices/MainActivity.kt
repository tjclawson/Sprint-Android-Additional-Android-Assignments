package com.example.mediaservices

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val handler = Handler()
    val delay = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_pause_play.isEnabled = false
        playPauseButtonInit()
        seekBarScrub()
        handlerInit()

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

    private fun handlerInit() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                seekBarUpdate()
                handler.postDelayed(this, delay.toLong())
            }
        }, delay.toLong())
    }

    private fun seekBarUpdate () {
        seekbar.progress = vv_main.currentPosition
    }

    private fun seekBarScrub() {
        seekbar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {}

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {
                p0?.let { vv_main.seekTo(it.progress) }
            }
        })
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
