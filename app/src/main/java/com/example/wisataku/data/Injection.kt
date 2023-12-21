package com.example.wisataku.data

import android.content.Context
import com.example.wisataku.data.retrofit.ApiConfig

object Injection {

    fun provideRepository(context: Context): UserRepository {

        val apiConfig = ApiConfig.getApiService()
        val userPreference = UserPreference.getInstance(context.dataStore)

        return UserRepository.getInstance(apiConfig, userPreference)

    }

}