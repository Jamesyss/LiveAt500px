package com.foodstory.liveat500px.dao

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


data class PhotoItemCollectionDao constructor(
    @SerializedName("success") var success: Boolean = false,
    @SerializedName("data") var data: ArrayList<PhotoItemDao?> = arrayListOf()
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.createTypedArrayList(PhotoItemDao.CREATOR) as ArrayList<PhotoItemDao?>
    ) {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (success) 1 else 0)
        parcel.writeTypedList(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PhotoItemCollectionDao> {
        override fun createFromParcel(parcel: Parcel): PhotoItemCollectionDao {
            return PhotoItemCollectionDao(parcel)
        }

        override fun newArray(size: Int): Array<PhotoItemCollectionDao?> {
            return arrayOfNulls(size)
        }
    }
}