package com.asgar72.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.asgar72.githubuser.databinding.ActivityUserDataBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserData : AppCompatActivity() {

    private val binding by lazy {
        ActivityUserDataBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        val username = intent.getStringExtra("username")
        if (username != null) {
            // Initialize Retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val gitHubApi = retrofit.create(GitHubApi::class.java)

            // Make API call to get user data
            val call = gitHubApi.getUser(username)

            call.enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user = response.body()

                        // Check if user is not null
                        if (user != null) {
                            // Update the UI elements with user data
                            binding.nameUser.text = user.name
                            binding.txtUser.text = "@${user.login}"
                            binding.aboutUser.text = user.bio
                            binding.txtFollowers.text = user.followers.toString()
                            binding.txtFollowing.text = user.following.toString()
                            binding.txtrepo.text = user.public_repos.toString()
                        } else {
                            Log.e("UserData Activity", "Response body is null")
                        }
                    } else {
                        // Handle specific HTTP error codes
                        when (response.code()) {
                            404 -> {
                                // User not found, display an error message
                                Log.e("UserData Activity", "User not found")
                                // You can show a toast or set an error message in your UI here
                            }
                            else -> {
                                // Handle other HTTP error codes
                                Log.e("UserData Activity", "HTTP Error: ${response.code()}")
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    // Handle network errors here
                    Log.e("UserData Activity", "onFailure: ${t.message}")
                }
            })
        }
    }
}
