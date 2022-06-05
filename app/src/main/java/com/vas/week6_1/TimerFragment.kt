package com.vas.week6_1

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vas.week6_1.databinding.FragmentTimerBinding

class TimerFragment : Fragment() {

    private var binding: FragmentTimerBinding? = null

    private var countColor = 0
    private var seconds = 0
    private var handler : Handler? = null

    private var fragmentSendClick : OnFragmentSendClick? = null

    override fun onAttach(context: Context) {
        fragmentSendClick = context as (OnFragmentSendClick)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("seconds", seconds)
        outState.putInt("count", countColor)
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        seconds = savedInstanceState?.getInt("seconds") ?: 0
        countColor = savedInstanceState?.getInt("count") ?: 0
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        runTimer()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        handler = null
    }

    private fun setupUI() {
        setTimerTextView("00:00")
        binding?.apply {
            playImageView.setOnClickListener { fragmentSendClick?.onSendClick("start") }
            pauseImageView.setOnClickListener { fragmentSendClick?.onSendClick("pause") }
            rstImageView.setOnClickListener { fragmentSendClick?.onSendClick("stop") }
        }
    }

    private fun setTimerTextView(time: String) {
        binding?.timerTextView?.text = time
    }

    private fun runTimer(){
        handler = Handler(Looper.getMainLooper())
        handler?.post(object : Runnable{
            override fun run() {
                var secondsTimer = seconds%60
                var minutesTimer = seconds/60

                if (countColor==20){
                    binding?.backgroundTimer?.setBackgroundColor(Color.argb(
                        255, (0..255).random(), (0..255).random(), (0..255).random()))
                    countColor=0
                } else {
                    countColor ++
                }
                var time = String.format("%02d:%02d", minutesTimer, secondsTimer)

                setTimerTextView(time)
                seconds ++
                handler?.postDelayed(this, 1000)
            }
        })
    }

    interface OnFragmentSendClick {
        fun onSendClick(click: String)
    }

}