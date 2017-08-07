package com.fanleiyi.tarena.cartoonlivehybrid.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;

import com.fanleiyi.tarena.cartoonlivehybrid.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    @AfterViews
    public void afterView(){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,LoginActivity_.class));
                finish();
            }
        },1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
