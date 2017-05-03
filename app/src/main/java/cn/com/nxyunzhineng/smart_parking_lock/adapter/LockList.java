package cn.com.nxyunzhineng.smart_parking_lock.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.com.nxyunzhineng.smart_parking_lock.R;
import cn.com.nxyunzhineng.smart_parking_lock.beans.LockItemBean;

/**
 * 作者：  wenze
 * 时间：  2017/4/28.
 * 版本：
 * 内容：
 */

public class LockList extends BaseAdapter {

    private ArrayList<LockItemBean> mLockItem;
    private LayoutInflater mInflater;
    public LockList(Activity activity,ArrayList ak){
        mInflater = activity.getLayoutInflater();
        mLockItem = ak;
    }
    @Override
    public int getCount() {
        return mLockItem.size();
    }

    @Override
    public Object getItem(int position) {
        return mLockItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder mViewHolder ;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.lockitem,null);
            mViewHolder = new ViewHolder();
            mViewHolder.mLockName = (TextView) convertView.findViewById(R.id.my_lock_name);
            mViewHolder.mState = (TextView) convertView.findViewById(R.id.my_lock_state);
            convertView.setTag(mViewHolder);
        }else
        {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.mLockName.setText(mLockItem.get(position).getmLockName());
        mViewHolder.mState.setText(mLockItem.get(position).getmLockState());
        return convertView;
    }
    static class ViewHolder{
        TextView mLockName;
        TextView mState;
    }
}
