package com.example.iwyatt.circleanimationdrawable.widgets

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.view.animation.AnimationUtils

/**
 * Created by wt on 17/5/25.
 */
class CircleAnimationDrawable(var mRadius: Float) : Drawable(), Animatable, Runnable {

    companion object {
        val DEFAULT_ANIMATION_DURATION: Int = 1000
        val DEFAULT_ANIMATION_TOTAL_DURATION: Int = DEFAULT_ANIMATION_DURATION * 5
    }

    var mPaint: Paint = Paint()
    var mStartTicks: Array<Long?>? = null
    // public会和重写的isRunning()冲突
    private var isRunning: Boolean = false

    init {
        mPaint.style = Paint.Style.FILL
        mPaint.isAntiAlias = true
        mPaint.color = 0xffff00ff.toInt()

    }

    override fun draw(canvas: Canvas?) {
        if (null == canvas) {
            return
        }
        mStartTicks?.forEach { tick ->
            val curPercent = calculateCurPercent(tick)
            val alpha = -(curPercent + curPercent) + 1
            mPaint.alpha = (255 * alpha).toInt()

            val radius = curPercent * mRadius
            val bounds = bounds
            val x = bounds.centerX().toFloat()
            val y = bounds.centerY().toFloat()
            canvas.drawCircle(x, y, radius, mPaint)
        }
    }

    fun calculateCurPercent(startTicks: Long?): Float {
        var curPercent = 0.0f
        if (isRunning() && null != startTicks) {
            val loopMillis = DEFAULT_ANIMATION_TOTAL_DURATION
            curPercent = (AnimationUtils.currentAnimationTimeMillis() - startTicks).toFloat() / loopMillis.toFloat()
            while (curPercent > 1) {
                curPercent--
            }
        }
        return curPercent
    }

    override fun isRunning(): Boolean {
        return isRunning
    }

    override fun start() {
        if (!isRunning()) {
            isRunning = true
            val curTime = AnimationUtils.currentAnimationTimeMillis()
            mStartTicks = Array(5, { index -> curTime + DEFAULT_ANIMATION_DURATION * index })
            run()
        }
    }

    override fun stop() {
        if (isRunning()) {
            unscheduleSelf(this)
            isRunning = false
        }
    }

    override fun run() {
        invalidateSelf()
        scheduleSelf(this, AnimationUtils.currentAnimationTimeMillis() + (1000 / 60))
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.colorFilter = colorFilter
    }

    override fun getIntrinsicWidth(): Int {
        return 2 * mRadius.toInt()
    }

    override fun getIntrinsicHeight(): Int {
        return 2 * mRadius.toInt()
    }

    override fun getMinimumWidth(): Int {
        return 2 * mRadius.toInt()
    }

    override fun getMinimumHeight(): Int {
        return 2 * mRadius.toInt()
    }

}