package com.foodstory.liveat500px.view

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState

/**
 * Created by nuuneoi on 11/16/2014.
 */
class CustomViewTemplate : View {
    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
        initWithAttrs(attrs, 0, 0)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
        initWithAttrs(attrs, defStyleAttr, 0)
    }

    @TargetApi(21)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
        initWithAttrs(attrs, defStyleAttr, defStyleRes)
    }

    private fun init() {
        isSaveEnabled = true
    }

    private fun initWithAttrs(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);
        return BundleSavedState(superState)
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        val ss = state as BundleSavedState
        super.onRestoreInstanceState(ss.superState)
        val bundle = ss.bundle
        // Restore State from bundle here
    }
}