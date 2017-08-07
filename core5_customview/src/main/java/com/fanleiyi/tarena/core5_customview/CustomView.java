package com.fanleiyi.tarena.core5_customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tarena on 2017/7/31.
 */

public class CustomView extends View {

    int viewWidth;
    int viewHeight;
    int gap;
    ArrayList<HashMap<String,String>> list;
    int maxPrice;
    public void setData( ArrayList<HashMap<String,String>> list)
    {
        this.list=list;
        for (int i=0;i<list.size();i++)
        {
            int currentPrice=Integer.parseInt(list.get(i).get("price"));
            if (currentPrice>maxPrice)
            {
                maxPrice=currentPrice;
            }
        }
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        Rect rect=new Rect(0,0,viewWidth,viewHeight);
        canvas.drawRect(rect,paint);

        paint.setTextSize(24);
        //得字符串的宽度
        String lastTime=list.get(list.size()-1).get("time");
        Rect sizeRect=new Rect();
        paint.getTextBounds(lastTime,0,lastTime.length(),sizeRect);

        int lastTimeWidth=sizeRect.width();
        int lastTimeHeight=sizeRect.height();
        gap=(viewWidth-lastTimeWidth)/(list.size()-1);
        paint.setColor(Color.WHITE);

        int priceHeight=viewHeight-lastTimeHeight;
        for (int i=0;i<list.size();i++)
        {
            HashMap<String,String> map=list.get(i);
            String time=map.get("time");
            int timeX=i*gap;
            int timeY=viewHeight;
            canvas.drawText(time,timeX,timeY,paint);

            //画价格
            float currentPrice=Float.parseFloat(map.get("price"));

            float priceY=currentPrice/maxPrice*priceHeight;
            //大的值显示在下面，应该显示在上面
            priceY=priceHeight-priceY;
            //最大值显示在控件的外面
            priceY=priceY+lastTimeHeight;
            canvas.drawText(map.get("price"),timeX,priceY,paint);

            //画线
            if (i<list.size()-1) {
                float nextPrice = Float.parseFloat(list.get(i + 1).get("price"));
                float nextPriceY = nextPrice / maxPrice * priceHeight;
                nextPriceY = priceHeight - nextPriceY;
                nextPriceY = nextPriceY + lastTimeHeight;
                int nextPriceX = gap * (i + 1);
                canvas.drawLine(timeX, priceY, nextPriceX, nextPriceY, paint);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth=w;
        viewHeight=h;
    }
}
