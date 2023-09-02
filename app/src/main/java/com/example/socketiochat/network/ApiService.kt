package com.ischeck.network


import android.content.Context
import com.example.socketiochat.model.LoginResponse
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


interface ApiService {

    @FormUrlEncoded
    @POST("auth/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): LoginResponse

    @POST("auth/logout")
    suspend fun logout(): ResponseBody

    companion object {

        private const val BASE_URL = "base url"

        fun create(context: Context): ApiService {

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okhttpClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }

        private fun okhttpClient(context: Context): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .build()
        }

    }

}