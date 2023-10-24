package com.asgar72.todotimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val bottomView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        bottomView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.item1 -> replaceWithFragment(ToDo())
                R.id.item2 -> replaceWithFragment(StopWatch())
                else -> {

                }
            }
            true
        }

        replaceWithFragment(ToDo())
    }
    private  fun replaceWithFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}