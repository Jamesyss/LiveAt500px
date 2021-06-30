package com.foodstory.liveat500px.datatype

import android.os.Bundle

data class MutableInteger (
    var value: Int = 0
){
    fun onSavedInstanceState() : Bundle {
        val bundle = Bundle()
        bundle.putInt("value",value)
        return bundle
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        value = savedInstanceState.getInt("value")
    }
}






