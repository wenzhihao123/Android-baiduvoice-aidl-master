package com.wzh.baiduvoiceaidl;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;


/**
 * author：Administrator on 2017/3/11 09:32
 * description:文件说明
 * version:版本
 */
public class WaveView extends View {
    private int mWidth ;  //控件宽度
    private int mHeight ;  //控件高度
    private Paint mPaint ;
    private int waveWidth = 400; //波长
    private int waveHeight = 400; //波长的振幅
    private int originalHeight = 800 ; //高度
    private Path mPath ;
    private int x =0 ;
    private int y =0 ;

    public WaveView(Context context) {
        this(context,null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.parseColor("#89cff0"));

        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2 - 25, mPaint);
        setDataInfo();
        canvas.drawPath(mPath,mPaint);
    }

    private void setDataInfo() {
        mPath.reset();
        mPath.moveTo(-mWidth,originalHeight);

        mPath.quadTo(-(mWidth/4)*3+x,originalHeight+waveHeight/2,-mWidth/2+x,originalHeight);
        mPath.quadTo( -mWidth/4+x,originalHeight - waveHeight/2,x,originalHeight);

        mPath.quadTo( mWidth/4+x,originalHeight - waveHeight/2,mWidth/2+x,originalHeight);
        mPath.quadTo((mWidth/4)*3+x,originalHeight+waveHeight/2,mWidth+x,originalHeight);

        mPath.lineTo(mWidth,originalHeight);
        mPath.lineTo(mWidth,mHeight);
        mPath.lineTo(0,mHeight);
        mPath.setFillType(Path.FillType.WINDING);
        mPath.close();
        invalidate();
    }
    public void startAnimation(){
        ValueAnimator animate = ValueAnimator.ofFloat(1.0f,0);
        animate.setDuration(1000);
        animate.setInterpolator(new LinearInterpolator());
        animate.setRepeatCount(Animation.INFINITE);
        animate.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float factor = (float) valueAnimator.getAnimatedValue();
                x = -(int) (factor*mWidth);
                Log.e("wenzhihao",x+"---->");
                invalidate();
            }
        });
        animate.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        init();
    }
}
