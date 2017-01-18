package com.example.iwyatt.circleanimationdrawable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.iwyatt.circleanimationdrawable.widgets.CircleAnimationDrawable;

public class MainActivity extends AppCompatActivity {

    private CircleAnimationDrawable mAnimationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView img = (ImageView) findViewById(R.id.img);

        mAnimationDrawable = new CircleAnimationDrawable(200);
        img.setImageDrawable(mAnimationDrawable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mAnimationDrawable) {
            mAnimationDrawable.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (null != mAnimationDrawable) {
            mAnimationDrawable.stop();
        }
    }
}
