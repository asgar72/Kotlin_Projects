package com.asgar72.githubuser

data class User(
    val login: String,
    val name: String,
    val public_repos: Int,
    val followers: Int,
    val following: Int,
    val bio: String,
    val avatar_url: String,
    val location: String,
)
