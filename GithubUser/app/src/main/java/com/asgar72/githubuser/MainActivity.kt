package com.asgar72.githubuser

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
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

            if (query.isNotBlank()) {
                // Initialize Retrofit
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val gitHubApi = retrofit.create(GitHubApi::class.java)

                // Make API call to get user data
                val call = gitHubApi.getUser(query)

                call.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            // User exists, start UserData activity
                            val intent = Intent(this@MainActivity, UserData::class.java)
                            intent.putExtra("username", query)
                            startActivity(intent)
                        } else {
                            // User not found, show a message
                            Toast.makeText(this@MainActivity, "User not found", Toast.LENGTH_SHORT).show()
                            showCustomDialog()
                        }
                    }
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        // Handle network errors here
                        Log.e("MainActivity", "onFailure: ${t.message}")
                    }
                })
            } else {
                Toast.makeText(this, "Enter UserName", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun showCustomDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.nodata)
        dialog.show()
    }
}
