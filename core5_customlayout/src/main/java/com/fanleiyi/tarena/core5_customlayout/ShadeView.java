package com.fanleiyi.tarena.core5_customlayout;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by pjy on 2017/8/1.
 */

public class ShadeView extends View {
    String text;
    float textSize;
    int textColor;
    int shadeColor;
    float shadeSize;
    int stringHeight;
    public ShadeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.ShadeView);
         text=typedArray.getString(R.styleable.ShadeView_text);
        textSize=typedArray.getFloat(R.styleable.ShadeView_text_size,1);
        textColor=typedArray.getColor(R.styleable.ShadeView_text_color, Color.BLACK);
        shadeColor=typedArray.getColor(R.styleable.ShadeView_shade_color, Color.BLACK);
        shadeSize=textSize/10;
        typedArray.recycle();
        typedArray=null;

        dp2px(textSize);
    }
    public  int dp2px(float dp)
    {
        //mdpi 160  1dp=1px
        //hdpi 240  1dp=1.5px
        //xhdpi 320  1dp=2px
        //xxhdpi 480  1dp=3px
        //xxxhdpi 640  1dp=4px
        Resources res=getContext().getResources();
        float density=res.getDisplayMetrics().scaledDensity;
        //Log.i("density",""+density);
        //Toast.makeText(getContext(),""+density,Toast.LENGTH_LONG).show();
//5.5+0.5
        return (int)(density*dp+0.5);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Paint paint=new Paint();
        paint.setTextSize(textSize);
        Rect rect=new Rect();
        paint.getTextBounds(text,0,text.length(),rect);
        stringHeight=rect.height();
        int viewWidth=(int)(rect.width()+shadeSize);
        int viewHeight=(int)(rect.height()+shadeSize);
        setMeasuredDimension(viewWidth,viewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint=new Paint();
        paint.setTextSize(textSize);
        //画阴影
        paint.setColor(shadeColor);
        canvas.drawText(text,shadeSize,stringHeight+shadeSize,paint);
        //画上面的文字
        paint.setColor(textColor);
        canvas.drawText(text,0,stringHeight,paint);
    }
}
