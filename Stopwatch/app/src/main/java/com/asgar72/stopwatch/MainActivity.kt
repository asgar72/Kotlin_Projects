package com.asgar72.stopwatch

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Chronometer
import android.widget.NumberPicker
import com.asgar72.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    var isRunning = false
    private var minutes: String? = "00.00.00"
    private var elapsedTimeWhenStopped: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        var lapList = ArrayList<String>()
        var arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lapList)
        binding.listView.adapter = arrayAdapter
        binding.lap.setOnClickListener {
            if (isRunning) {
                lapList.add(binding.chronometer.text.toString())
                arrayAdapter.notifyDataSetChanged()
            }
        }


        binding.restore.setOnClickListener {
            if (isRunning){
                binding.chronometer.base= SystemClock.elapsedRealtime()
                binding.run.text = "RUN"
                isRunning=false
                binding.chronometer.stop()

                //clear lap list
                lapList.clear()
                arrayAdapter.notifyDataSetChanged()
            }
        }

        binding.run.setOnClickListener {
            if (!isRunning) {
                isRunning = false
                if (!minutes.equals("00.00.00")) {
                    var totalmint = minutes!!.toInt() * 60 * 1000L
                    var countDown = 1000L
                    binding.chronometer.base = SystemClock.elapsedRealtime() + totalmint - elapsedTimeWhenStopped
                    binding.chronometer.format = "%S %S"
                    binding.chronometer.onChronometerTickListener =
                        Chronometer.OnChronometerTickListener {
                            var elapsedTime =
                                SystemClock.elapsedRealtime() - binding.chronometer.base
                            if (elapsedTime >= totalmint) {
                                binding.chronometer.stop()
                                isRunning = false
                                binding.run.text = "RUN"
                            }
                        }
                } else {
                    isRunning = true
                    binding.chronometer.base = SystemClock.elapsedRealtime() - elapsedTimeWhenStopped
                    binding.run.text = "STOP"
                    binding.chronometer.start()
                }
            } else {
                if (isRunning) {
                    // Save the elapsed time when stopping
                    elapsedTimeWhenStopped = SystemClock.elapsedRealtime() - binding.chronometer.base
                    binding.run.text = "RUN"
                    isRunning = false
                    binding.chronometer.stop()
                }
            }
        }
    }
}
