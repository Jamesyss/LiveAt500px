package com.foodstory.liveat500px.manager.http

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.foodstory.liveat500px.dao.PhotoItemCollectionDao
import com.foodstory.liveat500px.dao.PhotoItemDao
import com.google.gson.Gson
import com.inthecheesefactory.thecheeselibrary.manager.Contextor
import kotlin.math.max
import kotlin.math.min

/**
 * Created by nuuneoi on 11/16/2014.
 */
class PhotoListManager {

    private var mContext: Context
    private var dao: PhotoItemCollectionDao? = null

    constructor() {
        mContext = Contextor.getInstance().context
        loadCache()
    }

    /*
companion object {
    private var INSTANCE: PhotoListManager? = null
    fun getInstance(): PhotoListManager {
        return if (INSTANCE != null) INSTANCE!! else PhotoListManager().apply { INSTANCE = this }
    }
}
 */

    fun getDao(): PhotoItemCollectionDao? {
        return dao
    }

    fun setDao(dao: PhotoItemCollectionDao) {
        this.dao = dao
        // Save to Persistent Storage
        saveCache()
    }

    fun insertDaoAtTopPosition(newDao: PhotoItemCollectionDao) {
        if (dao == null)
            dao = PhotoItemCollectionDao(false, arrayListOf())
        if (dao?.data == null)
            dao?.data = ArrayList()
        dao?.data?.addAll(0, newDao.data)
       // dao?.data?.remove(null)
       // dao?.data?.add(null)
        saveCache()
    }

    fun appendDaoAtBottomPosition(newDao: PhotoItemCollectionDao) {
        if (dao == null)
            dao = PhotoItemCollectionDao(false, arrayListOf())
        if (dao?.data == null)
            dao?.data = ArrayList()
     //   dao?.data?.remove(null)
        dao?.data?.addAll(dao?.data?.size ?: 0, newDao.data)
      //  dao?.data?.add(null)
        saveCache()
    }

    fun getMaximumId(): Int {
        if (dao?.data == null) return 0

        var maxId = dao?.data?.get(0)?.id

        dao?.data?.forEachIndexed { index, photoItemDao ->
            maxId = max(maxId ?: 0, photoItemDao?.id ?: 0)
        }
        return maxId ?: 0
    }

    fun getMinimumId(): Int {
        if (dao?.data == null) return 0

        var minId = dao?.data?.get(0)?.id

        dao?.data?.forEachIndexed { index, photoItemDao ->
            minId = Math.min(minId ?: 0, photoItemDao?.id ?: 0)
        }
        return minId ?: 0
    }

    fun getCount(): Int {
        return dao?.data?.size ?: 0
    }

    fun onSaveInstanceState(): Bundle {
        val bundle = Bundle()
        bundle.putParcelable("dao",dao)
        return bundle
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        dao = savedInstanceState.getParcelable("dao")
    }

    private fun saveCache() {
        val minSize = min(20, dao?.data?.size ?: 0)
        val data = arrayListOf<PhotoItemDao?>()
        data.addAll(dao?.data?.subList(0, minSize)?.toList() ?: listOf())
        val cacheDao = PhotoItemCollectionDao(true, data)
        val json = Gson().toJson(cacheDao)

        val prefs = mContext.getSharedPreferences("photos", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        // Add/Edit/Delete
        editor.putString("json", json).apply()
        //editor.apply()
        Log.d("photos",prefs.getString("photos",null) ?: "null")
    }

    private fun loadCache() {
        val prefs = mContext.getSharedPreferences("photos", Context.MODE_PRIVATE)
        val json = prefs.getString("json", null) ?: return
        dao = Gson().fromJson(json, PhotoItemCollectionDao::class.java)
    }

}