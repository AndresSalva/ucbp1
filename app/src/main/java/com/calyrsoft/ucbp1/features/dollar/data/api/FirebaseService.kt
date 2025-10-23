package com.calyrsoft.ucbp1.features.dollar.data.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Query

interface FirebaseService {

    @PATCH("message.json")
    suspend fun updateMessage(
        @Query(value="auth") token: String,
        @Body body: Map<String, String>
    ): Response<Void>
}