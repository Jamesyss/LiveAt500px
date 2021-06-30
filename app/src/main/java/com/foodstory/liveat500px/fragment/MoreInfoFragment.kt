package com.foodstory.liveat500px.fragment

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.foodstory.liveat500px.R
import com.foodstory.liveat500px.dao.PhotoItemDao
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_more_info.*

/**
 * Created by nuuneoi on 11/16/2014.
 */
class MoreInfoFragment : Fragment() {

    var dao: PhotoItemDao? = null

    companion object {
        fun newInstance(dao: PhotoItemDao?): MoreInfoFragment {
            val fragment = MoreInfoFragment()
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

        savedInstanceState?.let { savedInstanceState ->
            onRestoreInstanceState(savedInstanceState)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_more_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initInstances(savedInstanceState)
    }

    private fun init(savedInstanceState: Bundle?) {
        // Init Fragment level's variable(s) here
        setHasOptionsMenu(true)
    }

    private fun initInstances(savedInstanceState: Bundle?) {
        // Init 'View' instance(s) with rootView.findViewById here
        // Note: State of variable initialized here could not be saved
        //       in onSavedInstanceState

        /*viewPager = rootView.findViewById(R.id.viewPager)
        viewPager.adapter = object : FragmentStatePagerAdapter(childFragmentManager) {
            override fun getCount(): Int {
                return 3
            }

            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        PhotoSummaryFragment.newInstance(dao)
                    }
                    1 -> {
                        PhotoInfoFragment.newInstance(dao)
                    }
                    2 -> {
                        PhotoTagFragment.newInstance(dao)
                    }
                    else -> {
                        Fragment()
                    }
                }
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return when(position) {
                    0 -> {
                        "Summary"
                    }
                    1 -> {
                        "Info"
                    }
                    2 -> {
                        "Tags"
                    }
                    else -> {
                        ""
                    }
                }
            }
        }

        slidingTabLayout = rootView.findViewById(R.id.slidingTabLayout)
        slidingTabLayout.setViewPager(viewPager)*/

        viewPager2.adapter = object : FragmentStateAdapter(this) {

            override fun getItemCount(): Int {
                return 3
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        PhotoSummaryFragment.newInstance(dao!!)
                    }
                    1 -> {
                        PhotoInfoFragment.newInstance(dao!!)
                    }
                    2 -> {
                        PhotoTagFragment.newInstance(dao!!)
                    }
                    else -> {
                        Fragment()
                    }
                }
            }
        }

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Summary"
                }
                1 -> {
                    tab.text = "Info"
                }
                2 -> {
                    tab.text = "Tags"
                }
            }
        }.attach()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Save Instance (Fragment level's variables) State here
    }

    private fun onRestoreInstanceState(savedInstanceState: Bundle) {
        // Restore Instance (Fragment level's variables) State here
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_more_info, menu)

        val menuItem: MenuItem = menu.findItem(R.id.action_share)
        val shareActionProvider = MenuItemCompat.getActionProvider(menuItem)
                as ShareActionProvider
        shareActionProvider.setShareIntent(getShareIntent())
    }

    private fun getShareIntent(): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
        intent.putExtra(Intent.EXTRA_TEXT, "Extra Text")
        return intent
    }
}