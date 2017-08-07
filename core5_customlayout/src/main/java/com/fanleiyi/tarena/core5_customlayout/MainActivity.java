package com.fanleiyi.tarena.core5_customlayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {
    AnimationView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //view= (AnimationView) findViewById(R.id.animationView);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //控件已经显示出来，才能用getWidth()，oncreate getWidth()=0
//        int width=view.getWidth();
//        int height=view.getHeight();
        Log.i("","");
        return super.onTouchEvent(event);
    }
}