package com.foodstory.liveat500px.manager

import android.annotation.SuppressLint
import android.content.Context
import com.foodstory.liveat500px.manager.http.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.inthecheesefactory.thecheeselibrary.manager.Contextor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by nuuneoi on 11/16/2014.
 */

class HttpManager {
    private var service: ApiService
    private var mContext: Context

    private constructor() {
        mContext = Contextor.getInstance().context

        val gson: Gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://nuuneoi.com/courses/500px/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        service = retrofit.create(ApiService::class.java)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var INSTANCE: HttpManager? = null
        fun getInstance(): HttpManager {
            return if (INSTANCE != null) INSTANCE!! else HttpManager().apply { INSTANCE = this }
        }
    }

    @JvmName("getService1")
    fun getService(): ApiService {
        return service
    }

}