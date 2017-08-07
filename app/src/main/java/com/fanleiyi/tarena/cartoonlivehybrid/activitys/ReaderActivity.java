package com.fanleiyi.tarena.cartoonlivehybrid.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fanleiyi.tarena.cartoonlivehybrid.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EActivity(R.layout.activity_reader)
public class ReaderActivity extends BaseActivity {
    ArrayList<String> list;
    @ViewById(R.id.img_reader)
    ImageView imageView;
    int currentIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list= (ArrayList<String>) getIntent().getSerializableExtra("list");
    }
    @AfterViews
    public void showFirst(){
        String imagePath=list.get(currentIndex);
        Glide.with(this).load(imagePath).into(imageView);

        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x= (int) event.getX();
                int width=v.getWidth();
                if (x>width/2){
                    currentIndex++;
                    if (currentIndex>list.size()-1){
                        currentIndex=0;
                    }
                }else{
                    currentIndex--;
                    if (currentIndex<0){
                        currentIndex=list.size()-1;
                    }
                }
                Glide.with(ReaderActivity.this).load(list.get(currentIndex)).into(imageView);
                return false;
            }
        });
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        list= (ArrayList<String>) intent.getSerializableExtra("list");
    }
}
