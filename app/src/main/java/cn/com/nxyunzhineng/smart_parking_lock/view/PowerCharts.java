package cn.com.nxyunzhineng.smart_parking_lock.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import cn.com.nxyunzhineng.smart_parking_lock.R;
/***
 * 用来显示电量
 */
public class PowerCharts extends View {

    private int mXcenter;  //
    private int mYcenter;
    private Paint mCirclePaint;
    private Paint mRingPaint;
    private Paint mTextPaint;
    private Paint mTextStringPaint;
    private int mCircleColor;
    private int mRingColor_Full;
    private int mRingColor_Middle;
    private int mRingColor_Alert;
    private float mRadius;
    private float mTextHeight;
    private float mTextWeight;
    private float mStrokeWidth;
    private float mRingRadius;
    private int maxProgress  = 100;
    private int nowProgress = 100;
    public PowerCharts(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context,attrs);
        initPaint();
    }
    private void initAttrs(Context context ,AttributeSet attrs){
        //初始化自定义控件
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.PowerCharts,0,0);
        mRadius = typedArray.getDimension(R.styleable.PowerCharts_radius,60);
        mStrokeWidth = typedArray.getDimension(R.styleable.PowerCharts_strokeWidth,30);
        mCircleColor = typedArray.getColor(R.styleable.PowerCharts_circleColor,0xffffff);
        mRingColor_Full = typedArray.getColor(R.styleable.PowerCharts_ringColor_full,0xffffff);
        mRingColor_Alert = typedArray.getColor(R.styleable.PowerCharts_ringColor_alert,0xffffff);
        mRingColor_Middle = typedArray.getColor(R.styleable.PowerCharts_ringColor_middle,0xffffff);
        mRingRadius = mRadius + (mStrokeWidth)/2;
    }

    /**
     * 初始化画笔(Paint)
     */
    private void initPaint(){
        //中心部分
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true); //设置锯齿效果
        mCirclePaint.setColor(mCircleColor);//设置颜色
        mCirclePaint.setStyle(Paint.Style.FILL);//设置为实心

        //外环部分
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor_Alert);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setARGB(255,255,255,255);
        mTextStringPaint = new Paint();
        mTextStringPaint .setAntiAlias(true);
        mTextStringPaint.setARGB(255,255,255,255);
        mTextPaint.setTextSize(mRadius / 2);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextStringPaint.setTextSize(mRadius/4);
        mTextStringPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = (float) Math.ceil(fontMetrics.descent - fontMetrics.ascent);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        mYcenter = canvas.getHeight() / 3;  //
        mXcenter = width / 2;

        Log.v("height:",canvas.getHeight()+"");
        canvas.drawCircle(mXcenter,mYcenter,mRadius*2,mCirclePaint); //绘制出圆心
        RectF rectF = new RectF();
        rectF.left = mXcenter - mRingRadius;
        rectF.top = mYcenter - mRingRadius;
        rectF.right = mXcenter + mRingRadius;
        rectF.bottom = mYcenter - mRingRadius ;
        float value = ((float)nowProgress / (float)maxProgress )*360;
        canvas.drawArc(rectF,-90,value,false,mRingPaint);
    }
    public void setProgress(int progress){
        this.nowProgress = progress;
        //50 绿色  20 黄色    <20 红
        if(progress>60)
            mRingPaint.setColor(mRingColor_Full);
        else if (progress > 20)
            mRingPaint.setColor(mRingColor_Middle);
        else
            mRingPaint.setColor(mRingColor_Alert);
        this.postInvalidate();
    }


}
