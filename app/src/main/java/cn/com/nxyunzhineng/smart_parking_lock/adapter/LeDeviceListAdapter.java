package cn.com.nxyunzhineng.smart_parking_lock.adapter;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cn.com.nxyunzhineng.smart_parking_lock.R;

/**
 *
 * @author  wenze_zhang 2016-11-20
 * @since   SDK 17
 * @version  1
 */

public class LeDeviceListAdapter extends BaseAdapter {


    private ArrayList<BluetoothDevice> mLeDevices;
    private LayoutInflater mInflator;


    /**
     * @param device 要加入列表的设备
     */
    public void addDevice(BluetoothDevice device){
        if(!mLeDevices.contains(device)){
            mLeDevices.add(device);
        }
    }
    public LeDeviceListAdapter(Activity activity){
        super();
        mLeDevices = new ArrayList<BluetoothDevice>();
        mInflator = activity.getLayoutInflater();
    }
    @Override
    public int getCount() {
        return mLeDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return mLeDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear(){
        mLeDevices.clear();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder ;
        if(convertView == null){
            convertView = mInflator.inflate(R.layout.ble_device_item,null);
            mViewHolder = new ViewHolder();
            mViewHolder.deviceAddress = (TextView) convertView.findViewById(R.id.device_address);
            mViewHolder.deviceName = (TextView) convertView.findViewById(R.id.device_name);
            mViewHolder.deviceOwner = (TextView) convertView.findViewById(R.id.device_owner);
            convertView.setTag(mViewHolder);
        }else
        {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        final String deviceName = mLeDevices.get(position).getName();
        if((deviceName!=null)&&(!deviceName.equals(""))){
            mViewHolder.deviceName.setText(mLeDevices.get(position).getName());
        }else
            mViewHolder.deviceName.setText("未知设备");
        mViewHolder.deviceAddress.setText(mLeDevices.get(position).getAddress());
        mViewHolder.deviceOwner.setText("未知所有者");
        return convertView;
    }


    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
        TextView deviceOwner;
    }
}
