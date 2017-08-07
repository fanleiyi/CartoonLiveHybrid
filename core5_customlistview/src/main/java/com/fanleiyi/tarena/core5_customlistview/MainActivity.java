package com.fanleiyi.tarena.core5_customlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> list=new ArrayList<>();
    MyAdapter myAdatper;
    CustomListView customListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i=0;i<20;i++)
        {
            list.add("user"+i);
        }
        customListView= (CustomListView) findViewById(R.id.customlist);
        myAdatper=new MyAdapter(this,list);
        customListView.setAdapter(myAdatper);
//6：把实现类传给框架
        MyOnRefreshingListener listener=new MyOnRefreshingListener();
        customListView.setOnRefreshingListener(listener);

    }
    //5:写实现类
    class MyOnRefreshingListener implements CustomListView.OnRefreshingListener
    {

        @Override
        public void onRefreshing(final  CustomListView listView) {
            Log.i("","联网");
            new Thread(){
                @Override
                public void run() {
                    try
                    {
                        //主线程的id是1
                        int threadId= (int) Thread.currentThread().getId();
                        Thread.currentThread().sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                int threadId= (int) Thread.currentThread().getId();
                                list.add("联网取到的数据");
                                myAdatper.notifyDataSetChanged();
                                listView.refreshComplete();
                            }
                        });
                    }catch (Exception e)
                    {}
                }
            }.start();
        }
    }
}
