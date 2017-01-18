# CircleAnimationDrawable
a simple sample with drawable

## 简介
 受这篇博客[#Android# 轮子杂评](https://zhuanlan.zhihu.com/p/23967243?refer=kotandroid)启发，重写了之前写的这个空间实现这个效果。[Github链接](https://github.com/wt1098078873/CircleAnimationDrawable) 在此。
## 用法
### step1
```java
    mAnimationDrawable = new CircleAnimationDrawable(200);
    img.setImageDrawable(mAnimationDrawable);
```
### step2
```java
    @Override
    protected void onResume() {
        super.onResume();
        if (null != mAnimationDrawable) {
            mAnimationDrawable.start();
        }
    }
```
### step3
```java
    @Override
    protected void onPause() {
        super.onPause();
        if (null != mAnimationDrawable) {
            mAnimationDrawable.stop();
        }
    }
```
## 实现效果
![效果](./gif/20170118104232.gif)

## View源码
```java
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.animation.AnimationUtils;

/**
 * Drawable基础用法 - 水圈循环
 * <p>
 * Created by wt on 17/1/17.
 */
public class CircleAnimationDrawable extends Drawable implements Animatable, Runnable {

    private static final int DEFAULT_ANIMATION_DURATION = 1000;

    private static final int DEFAULT_ANIMATION_TOTAL_DURATION = DEFAULT_ANIMATION_DURATION * 5;

    private Paint mPaint;

    private float mRadius;
    private long[] mStartTicks;
    private boolean isRunning = false;

    public CircleAnimationDrawable(float mRadius) {
        super();
        this.mRadius = mRadius;

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setColor(0xffff00ff);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (null == mStartTicks) {
            return;
        }
        for (int i = 0; i < mStartTicks.length; i++) {
            float curPercent = calculateCurPercent(mStartTicks[i]);
            float alpha = -(curPercent * curPercent) + 1;
            mPaint.setAlpha((int) (255 * alpha));

            float radius = curPercent * mRadius;
            Rect bounds = getBounds();
            float x = bounds.centerX();
            float y = bounds.centerY();
            canvas.drawCircle(x, y, radius, mPaint);
        }
    }

    private float calculateCurPercent(long startTicks) {
        float curPercent = 0.0f;
        if (isRunning()) {
            float loopMillis = DEFAULT_ANIMATION_TOTAL_DURATION;
            curPercent = (AnimationUtils.currentAnimationTimeMillis() - startTicks) / loopMillis;
            while (curPercent > 1) {
                curPercent--;
                startTicks += loopMillis;
            }
        }

        return curPercent;
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        if (null != mPaint) {
            mPaint.setColorFilter(colorFilter);
        }
    }

    public void setColorFilter(int color) {
        if (null != mPaint) {
            mPaint.setColor(color);
        }
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void start() {
        if (!isRunning()) {
            isRunning = true;
            long curTime = AnimationUtils.currentAnimationTimeMillis();
            mStartTicks = new long[5];
            for (int i = 0; i < mStartTicks.length; i++) {
                mStartTicks[i] = curTime + DEFAULT_ANIMATION_DURATION * i;
            }
            run();
        }
    }

    @Override
    public void stop() {
        if (isRunning()) {
            unscheduleSelf(this);
            isRunning = false;
        }
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void run() {
        invalidateSelf();
        scheduleSelf(this, AnimationUtils.currentAnimationTimeMillis() + (1000 / 60));
    }

    @Override
    public int getIntrinsicWidth() {
        return (int) (2 * mRadius);
    }

    @Override
    public int getIntrinsicHeight() {
        return (int) (2 * mRadius);
    }

    @Override
    public int getMinimumWidth() {
        return (int) (2 * mRadius);
    }

    @Override
    public int getMinimumHeight() {
        return (int) (2 * mRadius);
    }
}
```

## 声明
[LICENSE](LICENSE)
