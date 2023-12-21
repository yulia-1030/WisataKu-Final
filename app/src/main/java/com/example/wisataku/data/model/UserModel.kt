package com.example.wisataku.data.model

data class UserModel(
    var email: String,
    var password: String,
    val token: String,
    val isLogin: Boolean = false
)