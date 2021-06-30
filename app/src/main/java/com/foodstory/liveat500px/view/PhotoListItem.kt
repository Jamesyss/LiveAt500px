package com.foodstory.liveat500px.view

import android.annotation.TargetApi
import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.foodstory.liveat500px.R
import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState

/**
 * Created by nuuneoi on 11/16/2014.
 */
open class PhotoListItem : BaseCustomViewGroup {

    lateinit var tvName: TextView
    lateinit var tvDescription: TextView
    lateinit var ivImg: ImageView

    constructor(context: Context?) : super(context) {
        initInflate()
        initInstances()
    }

    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs) {
        initInflate()
        initInstances()
        initWithAttrs(attrs, 0, 0)
    }

    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initInflate()
        initInstances()
        initWithAttrs(attrs, defStyleAttr, 0)
    }

    @TargetApi(21)
    constructor(
        context: Context?,
        attrs: AttributeSet,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        initInflate()
        initInstances()
        initWithAttrs(attrs, defStyleAttr, defStyleRes)
    }

    private fun initInflate() {
        inflate(context, R.layout.list_item_photo, this)
    }

    private fun initInstances() {
        // findViewById here
        tvName = findViewById(R.id.tvName)
        tvDescription = findViewById(R.id.tvDescription)
        ivImg = findViewById(R.id.ivImg)
    }

    private fun initWithAttrs(attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) {
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec) // Width in px
        val height = width * 2 / 3
        val newHeightMeasureSpace = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        // Child Views
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpace)
        // Self
        setMeasuredDimension(width, height)
    }

    fun setNameText(text: String) {
        tvName.text = text
    }

    fun setDescriptionText(text: String) {
        tvDescription.text = text
    }

    fun setImageUrl(url: String) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.loading)
//            .error()
//            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform()
            .into(ivImg)
    }

}