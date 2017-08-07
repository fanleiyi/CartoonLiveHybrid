package com.fanleiyi.tarena.cartoonlivehybrid.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.fanleiyi.tarena.cartoonlivehybrid.R;
import com.fanleiyi.tarena.cartoonlivehybrid.util.SharedPreUtil;


public class LookStartActivity extends AppCompatActivity implements View.OnClickListener {
    Button livePlayerBtn, livePublisherBtn, multiPlayerBtn, vodPlayerBtn, nodeStreamerBtn,vrPlayerBtn;
    EditText playUrl, pubUrl, bufferTime, maxBufferTime, vodPlayUrl, vodBufferTime, vodMaxBufferTime,vrPlayUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_start);


        livePlayerBtn = (Button) findViewById(R.id.button1);


        playUrl = (EditText) findViewById(R.id.editTextPlay);
        bufferTime = (EditText) findViewById(R.id.editText_bufferTime);
        maxBufferTime = (EditText) findViewById(R.id.editText_maxBufferTime);

        playUrl.setText(SharedPreUtil.getString(this, "playUrl", "rtmp://xyplay.nodemedia.cn/live/stream_1001"));
        pubUrl.setText(SharedPreUtil.getString(this, "pubUrl", "rtmp://xypush.nodemedia.cn/live/stream_" + Math.round((Math.random() * 1000 + 1005))));
        bufferTime.setText(SharedPreUtil.getString(this, "bufferTime", "300"));
        maxBufferTime.setText(SharedPreUtil.getString(this, "maxBufferTime", "1000"));
        vodPlayUrl.setText(SharedPreUtil.getString(this, "vodPlayUrl", "http://vod.nodemedia.cn/qybs.flv"));
        vodBufferTime.setText(SharedPreUtil.getString(this, "vodBufferTime", "1000"));
        vodMaxBufferTime.setText(SharedPreUtil.getString(this, "vodMaxBufferTime", "20000"));
        vrPlayUrl.setText(SharedPreUtil.getString(this, "vrPlayUrl", "http://vod.nodemedia.cn/vrx.flv"));

        livePlayerBtn.setOnClickListener(this);
        livePublisherBtn.setOnClickListener(this);
        multiPlayerBtn.setOnClickListener(this);
        nodeStreamerBtn.setOnClickListener(this);
        vodPlayerBtn.setOnClickListener(this);
        vrPlayerBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        SharedPreUtil.put(LookStartActivity.this, "playUrl", playUrl.getText().toString());
        SharedPreUtil.put(LookStartActivity.this, "bufferTime", bufferTime.getText().toString());
        SharedPreUtil.put(LookStartActivity.this, "maxBufferTime", maxBufferTime.getText().toString());
        SharedPreUtil.put(LookStartActivity.this, "pubUrl", pubUrl.getText().toString());
        SharedPreUtil.put(LookStartActivity.this, "vodPlayUrl", vodPlayUrl.getText().toString());
        SharedPreUtil.put(LookStartActivity.this, "vodBufferTime", vodBufferTime.getText().toString());
        SharedPreUtil.put(LookStartActivity.this, "vodMaxBufferTime", vodMaxBufferTime.getText().toString());
        SharedPreUtil.put(LookStartActivity.this, "vrPlayUrl", vrPlayUrl.getText().toString());

        switch (v.getId()) {
            case R.id.button1:
                // 记住上次播放配置的信息，只在demo中使用，非SDK方法
                LookStartActivity.this.startActivity(new Intent(LookStartActivity.this, LookActivity.class));
                break;

        }
    }
}
