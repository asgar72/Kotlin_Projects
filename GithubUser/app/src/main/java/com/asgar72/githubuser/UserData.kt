package com.asgar72.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class UserData : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_data)
        supportActionBar?.hide()
    }
}