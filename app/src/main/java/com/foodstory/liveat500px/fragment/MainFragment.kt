package com.foodstory.liveat500px.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.foodstory.liveat500px.R
import com.foodstory.liveat500px.adapter.PhotoListAdapter
import com.foodstory.liveat500px.dao.PhotoItemCollectionDao
import com.foodstory.liveat500px.dao.PhotoItemDao
import com.foodstory.liveat500px.datatype.MutableInteger
import com.foodstory.liveat500px.manager.HttpManager
import com.foodstory.liveat500px.manager.http.PhotoListManager
import com.inthecheesefactory.thecheeselibrary.manager.Contextor
import kotlinx.android.synthetic.main.fragment_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by nuuneoi on 11/16/2014.
 */
class MainFragment : Fragment(), PhotoListAdapter.Listener {

    interface FragmentListener {
        fun onPhotoItemClicked(dao: PhotoItemDao)
    }

    // Variables
    lateinit var photoListAdapter: PhotoListAdapter
    lateinit var photoListManager: PhotoListManager
    lateinit var lastPositionInteger: MutableInteger

    companion object {
        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Fragment level's variables
        init(savedInstanceState)

        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState) // Restore Instance State
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initInstances(savedInstanceState)

    }

    private fun init(savedInstanceState: Bundle?) {
        photoListManager = PhotoListManager()
        lastPositionInteger = MutableInteger(-1)

        /*
        val dir: File? = context?.filesDir
        val dir: File? = context?.getDir("Hello", Context.MODE_PRIVATE)
        val dir: File? = context?.cacheDir
        Log.d("Storage", dir.toString())
        val file = File(dir, "testfile.txt")
        val fos = FileOutputStream(file)
        fos.write("Hello".toByteArray())
        fos.close()

        val prefs = context?.getSharedPreferences("dummy", Context.MODE_PRIVATE)
        val value: String? = prefs?.getString("Hello", null)
        val editor = prefs?.edit()
        // Add/Edit/Delete
        editor?.putString("Hello", "World")
        editor?.apply()
         */
    }

    private fun initInstances(savedInstanceState: Bundle?) {

        btnNewPhotos.setOnClickListener(buttonClickListener)

        // Init 'View' instance(s) with rootView.findViewById here
        photoListAdapter = PhotoListAdapter(lastPositionInteger, this)
        photoListAdapter.dao = photoListManager.getDao()
        recyclerView.adapter = photoListAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

        swipeRefreshLayout.setOnRefreshListener(pullToRefreshListener)

        recyclerView.addOnScrollListener(recyclerViewScrollListener)

        if (savedInstanceState == null)
            refreshData()
    }

    private fun refreshData() {
        if (photoListManager.getCount() == 0)
            reloadData()
        else
            reloadDataNewer()
    }

    private fun reloadDataNewer() {
        val maxId = photoListManager.getMaximumId()
        val call: Call<PhotoItemCollectionDao> =
            HttpManager.getInstance().getService().loadPhotoListAfterId(maxId)
        call.enqueue(
            PhotoListLoadCallBack(
                PhotoListLoadCallBack.MODE_RELOAD_NEWER,
                swipeRefreshLayout,
                photoListManager,
                photoListAdapter,
                recyclerView,
                btnNewPhotos
            )
        )
    }

    private fun reloadData() {
        val call: Call<PhotoItemCollectionDao> =
            HttpManager.getInstance().getService().loadPhotoList()
        call.enqueue(
            PhotoListLoadCallBack(
                PhotoListLoadCallBack.MODE_RELOAD,
                swipeRefreshLayout,
                photoListManager,
                photoListAdapter,
                recyclerView,
                btnNewPhotos
            )
        )
    }

    private var isLoadingMore = false

    private fun loadMoreData() {
        if (isLoadingMore)
            return
        isLoadingMore = true
        val minId = photoListManager.getMinimumId()
        val call: Call<PhotoItemCollectionDao> =
            HttpManager.getInstance().getService().loadPhotoListBeforeId(minId)
        call.enqueue(
            PhotoListLoadCallBack(
                PhotoListLoadCallBack.MODE_LOAD_MORE,
                swipeRefreshLayout,
                photoListManager,
                photoListAdapter,
                recyclerView,
                btnNewPhotos
            )
        )
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save Instance State here
        outState.putBundle(
            "photoListManager",
            photoListManager.onSaveInstanceState()
        )
        outState.putBundle(
            "lastPositionInteger",
            lastPositionInteger.onSavedInstanceState()
        )
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        //Restore instance state here
        savedInstanceState?.getBundle("photoListManager")?.let { savedInstanceState ->
            photoListManager.onRestoreInstanceState(savedInstanceState)
        }
        savedInstanceState?.getBundle("lastPositionInteger")?.let { savedInstanceState ->
            lastPositionInteger.onRestoreInstanceState(savedInstanceState)
        }
    }

    private fun hideButtonNewPhotos() {
        btnNewPhotos.visibility = View.GONE
        val anim = AnimationUtils.loadAnimation(
            Contextor.getInstance().context,
            R.anim.zoom_fade_out
        )
        btnNewPhotos.startAnimation(anim)
    }

    private val buttonClickListener: (v: View) -> Unit = {
        if (it == btnNewPhotos) {
            recyclerView.smoothScrollToPosition(0)
            hideButtonNewPhotos()
        }
    }

    private val pullToRefreshListener = { refreshData() }

    private val recyclerViewScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            //Log.d("OnScroll" , "onScrollStateChanged")
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            //Log.d("OnScroll" , "onScrolled")
            val manager = recyclerView.layoutManager as? LinearLayoutManager
            manager?.let {
                val firstVisibleItemPosition = manager.findFirstVisibleItemPosition()
                swipeRefreshLayout.isEnabled = firstVisibleItemPosition == 0
                val totalItemCount = manager.itemCount
                val lastVisibleItemPosition = manager.findLastVisibleItemPosition()

                if (firstVisibleItemPosition + lastVisibleItemPosition >= totalItemCount) {
                    if (photoListManager.getCount() > 0) {
                        //Load more
                        loadMoreData()
                        Log.d("OnScroll", "Load More.")
                    }
                }
                // Log.d("OnScroll" , "$firstVisibleItemPosition , $lastVisibleItemPosition , $totalItemCount")
            }

        }
    }

    class PhotoListLoadCallBack(
        private var mode: Int,
        private val swipeRefreshLayout: SwipeRefreshLayout,
        private val photoListManager: PhotoListManager,
        private val photoListAdapter: PhotoListAdapter,
        private val recyclerView: RecyclerView,
        private val btnNewPhotos: Button,
        private var isLoadingMore: Boolean = false
    ) : Callback<PhotoItemCollectionDao> {

        companion object {
            const val MODE_RELOAD = 1
            const val MODE_RELOAD_NEWER = 2
            const val MODE_LOAD_MORE = 3
        }

        override fun onResponse(
            call: Call<PhotoItemCollectionDao>,
            response: Response<PhotoItemCollectionDao>
        ) {
            Log.d("Callback", "${response.body()}")
            swipeRefreshLayout.isRefreshing = false

            if (response.isSuccessful) {

                val dao: PhotoItemCollectionDao? = response.body()

                val firstVisiblePosition = (recyclerView.layoutManager as LinearLayoutManager)
                    .findFirstVisibleItemPosition()
                val c = (recyclerView.layoutManager as LinearLayoutManager).getChildAt(0)
                val top = c?.top

                when (mode) {
                    MODE_RELOAD_NEWER -> {
                        photoListManager.insertDaoAtTopPosition(
                            dao ?: PhotoItemCollectionDao()
                        )
                    }
                    MODE_LOAD_MORE -> {
                        photoListManager.appendDaoAtBottomPosition(
                            dao ?: PhotoItemCollectionDao()
                        )
                    }
                    else -> {
                        photoListManager.setDao(dao ?: PhotoItemCollectionDao())
                    }
                }

                clearLoadingMoreFlagIfCapable(mode)
                photoListAdapter.dao = photoListManager.getDao()
                photoListAdapter.notifyDataSetChanged()

                if (mode == MODE_RELOAD_NEWER) {
                    val additionalSize = dao?.data?.size
                    photoListAdapter.increaseLastPosition(additionalSize ?: 0)
                    (recyclerView.layoutManager as LinearLayoutManager)
                        .scrollToPositionWithOffset(
                            firstVisiblePosition + additionalSize!!,
                            top ?: 0
                        )
                    if (additionalSize > 0) {
                        showButtonNewPhotos()
                    }
                } else {

                }

                if (dao?.data?.isNotEmpty() == true) {
                    showToast("Load Completed")
                }
            } else {
                // Handle
                clearLoadingMoreFlagIfCapable(mode)
                showToast(response.errorBody()?.string() ?: "")
            }
        }

        override fun onFailure(call: Call<PhotoItemCollectionDao>, t: Throwable) {
            clearLoadingMoreFlagIfCapable(mode)
            swipeRefreshLayout.isRefreshing = false
            showToast(t.toString())
        }

        private fun showToast(text: String) {
            Toast.makeText(
                Contextor.getInstance().context, text,
//            "Load Completed",
                Toast.LENGTH_SHORT
            ).show()
        }

        private fun showButtonNewPhotos() {
            btnNewPhotos.visibility = View.VISIBLE
            val anim = AnimationUtils.loadAnimation(
                Contextor.getInstance().context,
                R.anim.zoom_fade_in
            )
            btnNewPhotos.startAnimation(anim)
        }

        private fun clearLoadingMoreFlagIfCapable(mode: Int) {
            if (mode == MODE_LOAD_MORE)
                isLoadingMore = false
        }
    }

    override fun onItemClickListener(dao: PhotoItemDao, position: Int) {
        // Something click item.
//        Toast.makeText(context, "Position $position", Toast.LENGTH_SHORT).show()
        if (position < photoListManager.getCount()) {
            val dao = photoListManager.getDao()?.data?.get(position)
            val listener = activity as FragmentListener
            if (dao != null) {
                listener.onPhotoItemClicked(dao)
            }
        }
    }
}