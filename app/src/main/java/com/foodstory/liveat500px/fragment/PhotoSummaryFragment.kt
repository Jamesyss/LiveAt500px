package com.foodstory.liveat500px.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.foodstory.liveat500px.R
import com.foodstory.liveat500px.dao.PhotoItemDao
import kotlinx.android.synthetic.main.fragment_photo_summary.*

/**
 * Created by nuuneoi on 11/16/2014.
 */
class PhotoSummaryFragment : Fragment() {

    var dao: PhotoItemDao? = null

    companion object {
        fun newInstance(dao: PhotoItemDao): PhotoSummaryFragment {
            val fragment = PhotoSummaryFragment()
            val args = Bundle()
            args.putParcelable("dao", dao)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)

        dao = arguments?.getParcelable("dao")

        savedInstanceState?.let { onRestoreInstanceState(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photo_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInstances(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        // Init Fragment level's variable(s) here
    }

    @SuppressLint("SetTextI18n")
    private fun initInstances(savedInstanceState: Bundle?) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState

        tvName.text = dao?.caption
        tvDescription.text = dao?.username + "\n" + dao?.camera
        Glide.with(this)
            .load(dao?.imageUrl)
            .placeholder(R.drawable.loading)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(ivImg)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save Instance (Fragment level's variables) State here
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Restore Instance (Fragment level's variables) State here
    }
}