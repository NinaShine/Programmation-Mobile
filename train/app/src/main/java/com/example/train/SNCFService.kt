package com.example.train.api

import okhttp3.OkHttpClient
import okhttp3.Credentials
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SNCFService {
    @GET("coverage/sncf/journeys")
    suspend fun getTrainJourneys(
        @Header("Authorization") auth: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("datetime") datetime: String
    ): SNCFResponse
}

object SNCFClient {
    private const val BASE_URL = "https://api.sncf.com/v1/"
    private const val SNCF_API_KEY = "376bc7a6-c1f5-4b87-a220-a8394712e58a"

    private val client = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .header("Authorization", Credentials.basic(SNCF_API_KEY, ""))
            .build()
        chain.proceed(request)
    }.build()

    val service: SNCFService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create()) // ✅ Ajout correct du parser JSON
        .build()
        .create(SNCFService::class.java)
}
