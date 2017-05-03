package cn.com.nxyunzhineng.smart_parking_lock.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 作者：  wenze
 * 时间：  2017/5/2.
 * 版本：
 * 内容：
 */

public class HorizontalListView extends AdapterView<ListAdapter> implements  GestureDetector.OnGestureListener{


    private GestureDetector mGestureDetector;
    private ListAdapter listAdapter;
    public HorizontalListView(Context context) {

        super(context);
    }

    public HorizontalListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context,this);

    }

    @Override
    public ListAdapter getAdapter() {
        return listAdapter;
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        this.listAdapter = adapter;
        this.listAdapter.registerDataSetObserver(mDataObserver);
    }

    private DataSetObserver mDataObserver = new DataSetObserver() {

        @Override
        public void onChanged() {
            synchronized(HorizontalListView.this){
            //    mDataChanged = true;
            }
            invalidate();
            requestLayout();
        }

        @Override
        public void onInvalidated() {
            //reset();
            invalidate();
            requestLayout();
        }

    };

    @Override
    public View getSelectedView() {
        return null;
    }



    @Override
    public void setSelection(int position) {

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(listAdapter != null) {
            View child = listAdapter.getView(0, getChildAt(0), this);
            LayoutParams params = child.getLayoutParams();
            if (params == null)
                params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            addViewInLayout(child, -1, params, true);
            child.measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.AT_MOST));
        Log.d("show ,,,,,,","atable");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
        Log.d("hor touch","-------------------ev:"+ev.getY());
        return super.onTouchEvent(ev);
    }
}
