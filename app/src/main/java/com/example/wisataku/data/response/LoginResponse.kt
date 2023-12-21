package com.example.wisataku.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @field:SerializedName("data")
    val data: LoginResult? = null,

    @field:SerializedName("status")
    val status: String? = null
)

data class LoginResult(
    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("password")
    val password: String? = null
)