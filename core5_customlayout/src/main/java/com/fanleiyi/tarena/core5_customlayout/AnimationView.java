package com.fanleiyi.tarena.core5_customlayout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pjy on 2017/8/1.
 */

public class AnimationView extends View {
    Bitmap[] bitmaps;
    int index=0;
    Thread thread;
    float sleepTime;
    boolean isRunning=true;
    public AnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);


       //读取自定义属性
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.AnimationView);
        sleepTime=typedArray.getFloat(R.styleable.AnimationView_sleep_time,50);
        int imageArrayId=typedArray.getResourceId(R.styleable.AnimationView_images,1);


        //读取图片
        Resources resources=getResources();
        //imageTa放的是图片数组
        TypedArray imageTa=resources.obtainTypedArray(imageArrayId);
        int length=imageTa.length();
        bitmaps=new Bitmap[length];
        for (int i=0;i<length;i++)
        {
            int imageId=imageTa.getResourceId(i,0);
            Bitmap bitmap=BitmapFactory.decodeResource(resources,imageId);
            bitmaps[i]=bitmap;
        }

        MyRunnable myRunnable=new MyRunnable();
        thread=new Thread(myRunnable);
        thread.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int imageWidth=bitmaps[0].getWidth();
        int imageHeight=bitmaps[0].getHeight();
        //设置控件大小
        setMeasuredDimension(imageWidth,imageHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        Bitmap bitmap=bitmaps[index];
        canvas.drawBitmap(bitmap,0,0,paint);
    }

    class  MyRunnable implements Runnable
    {

        @Override
        public void run() {
            while (isRunning)
            {
                try {
                    index++;
                    if (index>=bitmaps.length)
                    {
                        index=0;
                    }
                    //调用onDraw
                    postInvalidate();
                    Thread.currentThread().sleep((long)sleepTime);
                }catch (Exception e)
                {

                }
            }
        }
    }
}
