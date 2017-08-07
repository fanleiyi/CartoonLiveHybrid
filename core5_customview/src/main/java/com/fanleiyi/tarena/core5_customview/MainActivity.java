package com.fanleiyi.tarena.core5_customview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Random random=new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CustomView customView= (CustomView) findViewById(R.id.customView);
        customView.setData(getData());
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                customView.setData(getData());
                //更新界面
                //我们不能直接调onDraw()
                //customView.onDraw();

                //invalidate无效，屏幕无效 重新绘制，系统调onDraw
                //间接调用的例子
                //1,handleMessage()  sendMessage间接调用handleMesasge
                //2,Receiver.onReceive  sendBroadCast间接调用Receiver.onReceive
                customView.invalidate();
                handler.postDelayed(this,100);
            }
        }, 100);
    }
    //9 3000
    //10 4000
    //11 5000
    //12 6000
    //arraylist<hashMap>

    public ArrayList<HashMap<String,String>> getData()
    {
        ArrayList<HashMap<String,String>> list=new ArrayList<>();
        HashMap<String,String> map=new HashMap();
        map.put("time","9");
        map.put("price",String.valueOf(random.nextInt(6000)));
        list.add(map);

        map=new HashMap();
        map.put("time","10");
        map.put("price",String.valueOf(random.nextInt(6000)));
        list.add(map);

        map=new HashMap();
        map.put("time","11");
        map.put("price",String.valueOf(random.nextInt(6000)));
        list.add(map);

        map=new HashMap();
        map.put("time","13");
        map.put("price",String.valueOf(random.nextInt(6000)));
        list.add(map);
        return list;

    }
}
