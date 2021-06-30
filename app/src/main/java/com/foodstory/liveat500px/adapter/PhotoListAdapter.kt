package com.foodstory.liveat500px.adapter

import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.foodstory.liveat500px.R
import com.foodstory.liveat500px.dao.PhotoItemCollectionDao
import com.foodstory.liveat500px.dao.PhotoItemDao
import com.foodstory.liveat500px.datatype.MutableInteger
import com.foodstory.liveat500px.view.PhotoListItem

class PhotoListAdapter(
    private var lastPositionInteger: MutableInteger,
    private val listener: Listener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dao: PhotoItemCollectionDao? = null

    private val VIEW_ITEM_TYPE = 0
    private val LOADING_ITEM_TYPE = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == LOADING_ITEM_TYPE) {
            return LoadingItemViewHolder(ProgressBar(parent.context))
        }

        val view = PhotoListItem(parent.context)

        if (viewType > lastPositionInteger.value) {
            val anim = AnimationUtils.loadAnimation(parent.context, R.anim.up_from_bottom)
            view.startAnimation(anim)
            lastPositionInteger.value = viewType
        }

        return PhotoListViewHolder(view)

        /*
        return if (viewType == PHOTO_TYPE) {
            val view = PhotoListItem(parent.context)
            PhotoListViewHolder(view)
        } else {
            val view = TextView(parent.context)
            TextItemViewHolder(view)
        }
         */
    }

    private fun getItem(position: Int): PhotoItemDao? {
        return dao?.data?.get(position)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PhotoListViewHolder) {
            getItem(position)?.let { dao ->
                holder.bind(dao, listener, position)
            }
        }
    }

    override fun getItemCount(): Int {
        if (dao == null)
            return 1
        if (dao?.data == null)
            return 1
        return (dao?.data?.size ?: 0) + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == itemCount - 1) LOADING_ITEM_TYPE else VIEW_ITEM_TYPE
        //  return dao?.data?.size ?: 0
//        if (position % 2 == 0) PHOTO_TYPE else TEXT_TYPE
    }

    fun increaseLastPosition(amount: Int) {
        lastPositionInteger.value = lastPositionInteger.value + amount
    }

    interface Listener {
        fun onItemClickListener(dao: PhotoItemDao, position: Int)
    }

}

class PhotoListViewHolder(private val photoItem: PhotoListItem) :
    RecyclerView.ViewHolder(photoItem) {

    fun bind(dao: PhotoItemDao, listener: PhotoListAdapter.Listener, position: Int) {
        //photoItem
        photoItem.setNameText(dao.caption ?: "")
        photoItem.setDescriptionText(dao.username + "\n" + dao.camera)
        photoItem.setImageUrl(dao.imageUrl ?: "")
        photoItem.setOnClickListener { listener.onItemClickListener(dao, position) }
    }

}

class LoadingItemViewHolder(private val textItem: ProgressBar) : RecyclerView.ViewHolder(textItem) {
    fun bind() {
        //textItem.text = text
    }
}