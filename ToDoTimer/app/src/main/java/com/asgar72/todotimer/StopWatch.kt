package com.asgar72.todotimer

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.ListView
import androidx.fragment.app.Fragment

class StopWatch : Fragment() {

    private var isRunning = false
    private var minutes: String? = "00.00.00"
    private var elapsedTimeWhenStopped: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_stop_watch, container, false)

        val lapList = ArrayList<String>()
        val arrayAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, lapList)
        val listView = view.findViewById<ListView>(R.id.listView)
        listView.adapter = arrayAdapter

        val chronometer = view.findViewById<Chronometer>(R.id.chronometer)
        val lapButton = view.findViewById<ImageView>(R.id.lap)
        val restoreButton = view.findViewById<ImageView>(R.id.restore)
        val runButton = view.findViewById<Button>(R.id.run)

        lapButton.setOnClickListener {
            if (isRunning) {
                lapList.add(chronometer.text.toString())
                arrayAdapter.notifyDataSetChanged()
            }
        }

        restoreButton.setOnClickListener {
            if (isRunning) {
                chronometer.base = SystemClock.elapsedRealtime()
                runButton.text = "RUN"
                isRunning = false
                chronometer.stop()
                lapList.clear()
                arrayAdapter.notifyDataSetChanged()
            }
        }

        runButton.setOnClickListener {
            if (!isRunning) {
                isRunning = false
                if (!minutes.equals("00.00.00")) {
                    val totalMint = minutes!!.toInt() * 60 * 1000L
                    val countDown = 1000L
                    chronometer.base =
                        SystemClock.elapsedRealtime() + totalMint - elapsedTimeWhenStopped
                    chronometer.format = "%S %S"
                    chronometer.onChronometerTickListener = Chronometer.OnChronometerTickListener {
                        val elapsedTime = SystemClock.elapsedRealtime() - chronometer.base
                        if (elapsedTime >= totalMint) {
                            chronometer.stop()
                            isRunning = false
                            runButton.text = "RUN"
                        }
                    }
                } else {
                    isRunning = true
                    chronometer.base = SystemClock.elapsedRealtime() - elapsedTimeWhenStopped
                    runButton.text = "STOP"
                    chronometer.start()
                }
            } else {
                if (isRunning) {
                    // Save the elapsed time when stopping
                    elapsedTimeWhenStopped = SystemClock.elapsedRealtime() - chronometer.base
                    runButton.text = "RUN"
                    isRunning = false
                    chronometer.stop()
                }
            }
        }
        return view
    }
}
