package com.foodstory.liveat500px.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.foodstory.liveat500px.R
import com.foodstory.liveat500px.dao.PhotoItemDao
import kotlinx.android.synthetic.main.fragment_tag_summary.*

/**
 * Created by nuuneoi on 11/16/2014.
 */
class PhotoTagFragment : Fragment() {

    var dao: PhotoItemDao? = null

    companion object {
        fun newInstance(dao: PhotoItemDao): PhotoTagFragment {
            val fragment = PhotoTagFragment()
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
        return inflater.inflate(R.layout.fragment_tag_summary, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInstances(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        // Init Fragment level's variable(s) here
    }

    private fun initInstances(savedInstanceState: Bundle?) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save Instance (Fragment level's variables) State here
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Restore Instance (Fragment level's variables) State here
    }
}