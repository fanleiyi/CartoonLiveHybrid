package com.fanleiyi.tarena.core5_popupwindow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

public class MainActivity extends AppCompatActivity {
    PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button moreBtn = (Button) findViewById(R.id.moreButton);
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow == null) {
                    View moreView = View.inflate(MainActivity.this, R.layout.more, null);
                    popupWindow = new PopupWindow(moreView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    int btnHeight = moreBtn.getHeight();
                    moreView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                    int viewHeight = moreView.getMeasuredHeight();
                    int y = btnHeight + viewHeight;
                    popupWindow.showAsDropDown(moreBtn, 0, -y);

                    final Button addButton = (Button) moreView.findViewById(R.id.addFriendButton);
                    addButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                            popupWindow = null;
                        }
                    });
                }
            }
        });
    }
}
