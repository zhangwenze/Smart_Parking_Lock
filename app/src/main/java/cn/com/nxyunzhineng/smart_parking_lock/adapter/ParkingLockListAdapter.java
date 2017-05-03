package cn.com.nxyunzhineng.smart_parking_lock.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 作者：  wenze
 * 时间：  2017/3/30.
 * 版本：
 * 内容： 用户拥有车位锁的列表
 */

public class ParkingLockListAdapter extends BaseAdapter{
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
    static class ViewHolder{
        TextView mLockName;
        TextView mIsUse;
        TextView mState;
    }
}
