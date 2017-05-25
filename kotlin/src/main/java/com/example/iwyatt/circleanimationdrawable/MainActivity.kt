package com.example.iwyatt.circleanimationdrawable

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import com.example.iwyatt.circleanimationdrawable.widgets.CircleAnimationDrawable

/**
 * Created by wt on 17/5/25.
 */
class MainActivity : Activity() {

    var mAnimDrawable: CircleAnimationDrawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.layout_main)

        val img = findViewById(R.id.img) as ImageView

        mAnimDrawable = CircleAnimationDrawable(200.toFloat())

        img.setImageDrawable(mAnimDrawable)

    }

    override fun onResume() {
        super.onResume()
        mAnimDrawable?.start()
    }

    override fun onPause() {
        super.onPause()
        mAnimDrawable?.stop()
    }

}