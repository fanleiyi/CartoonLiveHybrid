package com.fanleiyi.tarena.core5_customlistview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Created by tarena on 2017/7/31.
 */

public class CustomListView extends ListView {
    View headerView;
    int height;
    final static int STATE_DONE = 1;
    final static int STATE_PULL = 2;
    final static int STATE_RELEASE = 3;
    final static int STATE_REFRESHING = 4;
    int currentState;
    int downY;
    ProgressBar progressBar;
    ImageView ivArrow;
    TextView tvState;

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        headerView = View.inflate(context, R.layout.listview_header, null);
        progressBar = (ProgressBar) headerView.findViewById(R.id.progressBar);
        ivArrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        tvState = (TextView) headerView.findViewById(R.id.tv_state);

        addHeaderView(headerView);
        //  height=headerView.getHeight();
        //measure测量
        //MeasureSpec.UNSPECIFIED是一种测量模式：UNSPECIFIED表示不指定
        headerView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        height = headerView.getMeasuredHeight();
        headerView.setPadding(0, -height, 0, 0);
        //addFooterView();
        //设置初始状态
        currentState = STATE_DONE;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (currentState == STATE_DONE) {
                    currentState = STATE_PULL;
                    downY = (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (currentState == STATE_PULL) {
                    int currentY = (int) ev.getY();
                    int top=currentY-downY-height;
                    Log.i("listView","currentY="+currentY+",downY="+downY+",height="+height+",top="+top);
                    headerView.setPadding(0, top, 0, 0);
                    if (currentY-downY>height)
                    {
                        currentState=STATE_RELEASE;
                        tvState.setText("松开刷新");
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(currentState==STATE_RELEASE)
                {
                    currentState=STATE_REFRESHING;
                    tvState.setText("正在刷新");
                    ivArrow.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);
                    //联网代码
                    //4,调实现类
                    if (onRefreshingListener!=null)
                    {
                        //写框架的人把数据传给用框架的人
                        onRefreshingListener.onRefreshing(this);
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    //1:定义接口
    interface  OnRefreshingListener
    {
        void onRefreshing(CustomListView listView);
    }
    //2:申明成员变量
    OnRefreshingListener onRefreshingListener;
    //3,写一个方法接收实现类
    public  void setOnRefreshingListener(OnRefreshingListener l)
    {
        onRefreshingListener=l;
    }

    public  void refreshComplete()
    {
        headerView.setPadding(0,-height,0,0);
        tvState.setText("下拉刷新");
        ivArrow.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        currentState=STATE_DONE;
    }
}