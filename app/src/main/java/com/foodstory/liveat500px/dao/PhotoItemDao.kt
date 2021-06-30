package com.foodstory.liveat500px.dao

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*


class PhotoItemDao(
    @SerializedName("id") var id: Int?,
    @SerializedName("link") var link: String?,
    @SerializedName("image_url") var imageUrl: String?,
    @SerializedName("caption") var caption: String?,
    @SerializedName("user_id") var userId: Int?,
    @SerializedName("username") var username: String?,
    @SerializedName("profile_picture") var profilePicture: String?,
    @SerializedName("tags") var tags: ArrayList<String>?,
    @SerializedName("created_time") var createdTime: Date,
    @SerializedName("camera") var camera: String?,
    @SerializedName("lens") var lens: String?,
    @SerializedName("focal_length") var focalLength: String?,
    @SerializedName("iso") var iso: String?,
    @SerializedName("shutter_speed") var shutterSpeed: String?,
    @SerializedName("aperture") var aperture: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        Date(), //TODO("createdTime"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id ?: 0)
        parcel.writeString(link)
        parcel.writeString(imageUrl)
        parcel.writeString(caption)
        parcel.writeInt(userId ?: 0)
        parcel.writeString(username)
        parcel.writeString(profilePicture)
        parcel.writeStringList(tags)
        createdTime = Date() //TODO("createdTime"),
        parcel.writeString(camera)
        parcel.writeString(lens)
        parcel.writeString(focalLength)
        parcel.writeString(iso)
        parcel.writeString(shutterSpeed)
        parcel.writeString(aperture)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PhotoItemDao> {
        override fun createFromParcel(parcel: Parcel): PhotoItemDao {
            return PhotoItemDao(parcel)
        }

        override fun newArray(size: Int): Array<PhotoItemDao?> {
            return arrayOfNulls(size)
        }
    }
}
