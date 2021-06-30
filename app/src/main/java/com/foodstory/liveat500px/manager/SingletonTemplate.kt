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
class SingletonTemplate {

    private val mContext: Context

    private constructor() {
        mContext = Contextor.getInstance().context
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: SingletonTemplate? = null
            get() {
                if (field == null) field = SingletonTemplate()
                return field
            }
            private set
    }

}