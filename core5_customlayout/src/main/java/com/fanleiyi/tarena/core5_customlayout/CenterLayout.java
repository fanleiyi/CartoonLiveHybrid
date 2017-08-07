package com.fanleiyi.tarena.core5_customlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by pjy on 2017/8/1.
 */

public class CenterLayout extends ViewGroup {
    int groupWidth, groupHeight;
    public CenterLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量子控件
//        int childCount=getChildCount();
//        for (int i=0;i<childCount;i++)
//        {
//            View childView=getChildAt(i);
//            childView.measure(widthMeasureSpec,heightMeasureSpec);
//        }
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
      //模拟linearLayout
        //AnimationView animationView= (AnimationView) getChildAt(0);
        //ShadeView shadeView= (ShadeView) getChildAt(1);
//        animationView.layout(0,0,480,90);
//        shadeView.layout(0,90,480,190);

        //frameLayout
//        animationView.layout(0,0,480,90);
//        shadeView.layout(0,0,480,40);

        //相对布局
        //animationView.layout(0,0,90,90);
        //shadeView.layout(90,90,238,138);

        //绝对布局
        //animationView.layout(100,100,190,190);
        //shadeView.layout(200,300,200+96,300+48);

        int sum=0;
        int childCount=getChildCount();
        for (int i=0;i<childCount;i++)
        {
            View view=getChildAt(i);
            sum=sum+view.getMeasuredHeight();
        }
        int top=(groupHeight-sum)/2;
        for (int i=0;i<childCount;i++)
        {
            View view=getChildAt(i);
            int viewWidth=view.getMeasuredWidth();
            int viewHeight=view.getMeasuredHeight();
            int left=(groupWidth-viewWidth)/2;
            int right=left+viewWidth;
            int bottom=top+viewHeight;
            view.layout(left,top,right,bottom);
            top=top+viewHeight;

        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        groupHeight =h;
        groupWidth =w;
    }
}
