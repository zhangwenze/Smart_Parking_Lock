package cn.com.nxyunzhineng.smart_parking_lock.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
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
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();



        mYcenter = displayMetrics.heightPixels/4 - 80;
        mXcenter = width / 2;

        Log.d("canvas.height","----------------------->"+canvas.getHeight());
        canvas.drawCircle(mXcenter,mYcenter,width/4,mCirclePaint); //绘制出圆心
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

    /**
     *  测量宽度、高度
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        switch (widthMode)
        {
            case MeasureSpec.EXACTLY:
                break;
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.UNSPECIFIED:
                break;

        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom1) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int bottom = displayMetrics.heightPixels/2;

        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int bottom = displayMetrics.heightPixels/2;
        Log.d("DisplayMetrics.Height","------------------------->"+bottom);
        super.layout(l, t, r, bottom);
    }
}
