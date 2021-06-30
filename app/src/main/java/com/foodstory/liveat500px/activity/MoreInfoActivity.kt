package com.foodstory.liveat500px.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.foodstory.liveat500px.R
import com.foodstory.liveat500px.dao.PhotoItemDao
import com.foodstory.liveat500px.fragment.MoreInfoFragment

class MoreInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_info)

        initInstances()

        val dao = intent.getParcelableExtra<PhotoItemDao>("dao")

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.contentContainer, MoreInfoFragment.newInstance(dao))
                .commit()
        }
    }

    private fun initInstances() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}