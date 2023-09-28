package com.asgar72.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import com.asgar72.githubuser.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnsearch.setOnClickListener {
            val query = binding.userName.query.toString()
            // Do something with the query (e.g., perform a search)
            Toast.makeText(this, "$query", Toast.LENGTH_SHORT).show()

            // Start UserData activity
            val intent = Intent(this, UserData::class.java)
            intent.putExtra("username", query)
            startActivity(intent)
        }

    }
}
