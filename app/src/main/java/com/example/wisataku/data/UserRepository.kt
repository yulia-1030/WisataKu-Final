package com.example.wisataku.data

import androidx.lifecycle.liveData
import com.example.wisataku.data.model.UserModel
import com.example.wisataku.data.response.LoginResponse
import com.example.wisataku.data.response.RegisterResponse
import com.example.wisataku.data.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun register(
        name: String,
        email: String,
        password: String
    ) = liveData {

        emit(MessageState.Loading)

        try {

            val successResponse = apiService.register(name, email, password)
            val message = successResponse.message

            emit(MessageState.Success(message))

        }

        catch (e: HttpException) {

            val errorMessage: String

            if (e.code() == 400) {

                errorMessage = "Email already taken"

                emit(MessageState.Error(errorMessage))

            }

            else {

                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, RegisterResponse::class.java)
                errorMessage = errorBody.message.toString()

                emit(MessageState.Error(errorMessage))

            }

        }

    }

    fun login(
        email: String,
        password: String
    ) = liveData {

        emit(MessageState.Loading)

        try {

            val successResponse = apiService.login(email, password)
            val data = successResponse.data?.password

            emit(MessageState.Success(data))

        }

        catch (e: HttpException) {

            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, LoginResponse::class.java)

            emit(MessageState.Error(errorResponse.status!!))

        }

    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    suspend fun logout() {
        userPreference.logout()
        instance = null
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ) = instance ?: synchronized(this) {

            instance ?: UserRepository(apiService, userPreference)

        }.also {

            instance = it

        }

    }

}