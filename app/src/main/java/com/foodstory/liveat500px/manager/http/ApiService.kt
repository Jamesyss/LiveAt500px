package com.foodstory.liveat500px.manager.http

import com.foodstory.liveat500px.dao.PhotoItemCollectionDao
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("list")
    fun loadPhotoList () : Call<PhotoItemCollectionDao>

    @POST("list/after/{id}")
    fun loadPhotoListAfterId (@Path("id") id: Int) : Call<PhotoItemCollectionDao>

    @POST("list/before/{id}")
    fun loadPhotoListBeforeId (@Path("id") id: Int) : Call<PhotoItemCollectionDao>

}