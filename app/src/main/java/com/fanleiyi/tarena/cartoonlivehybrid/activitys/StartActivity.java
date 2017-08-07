package com.fanleiyi.tarena.cartoonlivehybrid.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fanleiyi.tarena.cartoonlivehybrid.R;

public class StartActivity extends Activity {
    public static final String RTMPURL_MESSAGE = "rtmppush.hx.com.rtmppush.rtmpurl";

    private Button _startRtmpPushButton = null;
    private EditText _rtmpUrlEditText = null;

    private View.OnClickListener _startRtmpPushOnClickedEvent = new View.OnClickListener() {
        @Override
        public void onClick(View arg0) {
            Intent i = new Intent(StartActivity.this, LiveActivity.class);
            String rtmpUrl = _rtmpUrlEditText.getText().toString();

            i.putExtra(StartActivity.RTMPURL_MESSAGE, rtmpUrl);
            StartActivity.this.startActivity(i);
        }
    };

    private void InitUI(){
        _rtmpUrlEditText = (EditText)findViewById(R.id.rtmpUrleditText);
        _startRtmpPushButton = (Button)findViewById(R.id.startRtmpButton);
        _rtmpUrlEditText.setText("rtmp://182.61.14.116:1935/live/12345");
        _startRtmpPushButton.setOnClickListener(_startRtmpPushOnClickedEvent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        InitUI();
    }
}
