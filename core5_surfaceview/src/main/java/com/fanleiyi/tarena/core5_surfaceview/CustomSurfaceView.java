package com.fanleiyi.tarena.core5_surfaceview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by pjy on 2017/7/31.
 */

public class CustomSurfaceView extends SurfaceView {
    //管理surfaceView
    SurfaceHolder surfaceHolder;
    boolean isRunning = true;
    Thread thread;
    Bitmap pigBitmap, flowerBitmap,bitmap1,bitmap2;
    int viewWidth, viewHeight;
    int imageX;
    int direction;
    final static int LEFT = 1;
    final static int RIGHT = 2;
    int index = 0;

    String[] info={"导演：张","主演：张健"};
    int infoY;
    public CustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = getHolder();
        MyCallBack myCallBack = new MyCallBack();
        surfaceHolder.addCallback(myCallBack);
        pigBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.f007);
        flowerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.f008);
        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.f015);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.f016);
        direction = LEFT;
    }

    class MyRunnable implements Runnable {
        @Override
        public void run() {
            while (isRunning) {

                Log.i("thread",index+"");
                Canvas canvas = null;
                try {
                    canvas = surfaceHolder.lockCanvas();
                    Paint paint = new Paint();
                    //画背景
                    paint.setColor(Color.RED);
                    Rect rect = new Rect(0, 0, viewWidth, viewHeight);
                    canvas.drawRect(rect, paint);

                    paint.setColor(Color.WHITE);
                    //画到内存
                    if (direction == LEFT) {
                        imageX = imageX - 20;
                    } else {
                        imageX = imageX + 20;
                    }

                    if (imageX < 0) {
                        direction = RIGHT;
                    }
                    if (imageX > viewWidth) {
                        direction = LEFT;
                    }
                    index++;
                    if (index % 4 == 0) {
                        canvas.drawBitmap(pigBitmap, imageX, 100, paint);
                    } else if (index%4==1){
                        canvas.drawBitmap(flowerBitmap, imageX, 100, paint);
                    }else if (index%4==2){
                        canvas.drawBitmap(bitmap1, imageX, 100, paint);
                    }else {
                        canvas.drawBitmap(bitmap2, imageX, 100, paint);
                    }
                    //文字从下向上移
                    paint.setTextSize(24);
                    infoY=infoY-20;
                    for (int i=0;i<info.length;i++)
                    {
                        //500 540
                        //480 520
                        int textY=infoY+i*40;
                        canvas.drawText(info[i],100,textY,paint);
                    }
                    Thread.currentThread().sleep(100);
                } catch (Exception e) {
                } finally {
                    //把内存中画好的界面画到屏幕上
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);

                    }catch (Exception e)
                    {

                    }
                }

            }
        }
    }

    class MyCallBack implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.i("", "");
            thread = new Thread(new MyRunnable());
            thread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            Log.i("", "");
            imageX = width;
            viewWidth = width;
            viewHeight = height;
            infoY=height;
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            Log.i("", "");
            isRunning=false;
            pigBitmap.recycle();
            pigBitmap=null;

            flowerBitmap.recycle();
            flowerBitmap=null;
        }
    }
}
