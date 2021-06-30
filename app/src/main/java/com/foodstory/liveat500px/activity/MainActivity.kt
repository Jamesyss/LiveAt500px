package com.foodstory.liveat500px.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.foodstory.liveat500px.R
import com.foodstory.liveat500px.dao.PhotoItemDao
import com.foodstory.liveat500px.fragment.MainFragment
import com.foodstory.liveat500px.fragment.MoreInfoFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainFragment.FragmentListener {

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    var moreInfoContainer: FrameLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initInstances()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.contentContainer, MainFragment.newInstance())
                .commit()
        }
    }

    private fun initInstances() {

        setSupportActionBar(toolBar)

        actionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawerLayout,
            R.string.open_drawer, R.string.close_drawer
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        actionBarDrawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        actionBarDrawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPhotoItemClicked(dao: PhotoItemDao) {
        moreInfoContainer = try {
            findViewById(R.id.moreInfoContainer)
        } catch (ex: NullPointerException) {
            null
        }

        if (moreInfoContainer == null) {
            // Mobile
            val intent = Intent(this, MoreInfoActivity::class.java)
            intent.putExtra("dao", dao)
            startActivity(intent)
        } else {
            // Tablet
            supportFragmentManager.beginTransaction()
                .replace(R.id.moreInfoContainer, MoreInfoFragment.newInstance(dao))
                .commit()
        }

    }
}