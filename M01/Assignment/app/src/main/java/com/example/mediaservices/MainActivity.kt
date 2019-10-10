package com.example.mediaservices

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val handler = Handler()
    val delay = 1000
    var videoDuration: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_pause_play.isEnabled = false
        playPauseButtonInit()
        seekBarScrub()
        handlerInit()

        button_launch_exo_activity.setOnClickListener {
            startActivity(Intent(this, ExoPlayer::class.java))
        }
    }

    override fun onStop() {
        super.onStop()
        vv_main.pause()
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
                timeUpdate()
                handler.postDelayed(this, delay.toLong())
            }
        }, delay.toLong())
    }

    private fun seekBarUpdate () {
        seekbar.progress = vv_main.currentPosition
    }

    private fun timeUpdate() {
        val timeElapsed = milliToMMSS((vv_main.currentPosition).toDouble())
        val timeRemaining = milliToMMSS((videoDuration - vv_main.currentPosition).toDouble())

        tv_time_elapsed.text = timeToString(timeElapsed)
        tv_time_remaining.text = timeToString(timeRemaining)
    }

    private fun milliToMMSS(milliseconds: Double): Time {
        var minutes: Double = (milliseconds / 1000.0) / 60.0
        var minutesRemainder = minutes - (minutes.toInt()).toDouble()
        var seconds = minutesRemainder * 60
        return Time(minutes.toInt(), seconds.toInt())
    }

    private fun timeToString(time: Time): String {
        var minutesString = ""
        var secondsString = ""
        if (time.minutes < 10) {
            minutesString = "0${time.minutes}"
        } else {
            minutesString = "${time.minutes}"
        }
        if (time.seconds < 10) {
            secondsString = "0${time.seconds}"
        } else {
            secondsString = "${time.seconds}"
        }

        return "$minutesString:$secondsString"
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
                videoDuration = it.duration
            }
        }
    }
}

class Time(val minutes: Int, val seconds: Int)

