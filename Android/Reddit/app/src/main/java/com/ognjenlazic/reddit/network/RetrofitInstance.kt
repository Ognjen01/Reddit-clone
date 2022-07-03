package com.ognjenlazic.reddit.network

import com.ognjenlazic.reddit.network.service.*
import com.ognjenlazic.reddit.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val user by lazy {
            retrofit.create(UserService::class.java)
        }

        val post by lazy {
            retrofit.create(PostService::class.java)
        }

        val community by lazy {
            retrofit.create(CommunityService::class.java)
        }

        val reaction by lazy {
            retrofit.create(ReactionService::class.java)
        }

        val report by lazy {
            retrofit.create(ReportService::class.java)
        }

        val rule by lazy {
            retrofit.create(RuleService::class.java)
        }

        val comment by lazy {
            retrofit.create(CommentService::class.java)
        }

        val flair by lazy {
            retrofit.create(FlairService::class.java)
        }
    }
}