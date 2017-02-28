package cn.com.nxyunzhineng.smart_parking_lock.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.UUID;

/**
 * Created by wenze on 2016/11/25.
 */

public class BluetoothLeService extends Service {

    private final static String TAG = BluetoothLeService.class.getSimpleName();


    private static final int STATE_DISCONNECTED = 0;   //未连接
    private static final int STATE_CONNECTING = 1;     //连接中
    private static final int STATE_CONNECTED = 2;      //已经连接
    public final static String ACTION_GATT_CONNECTED =
            "cn.com.nxyunzn,smart_parking_lock.le.ACTION_GATT_CONNECTED";
    public final static String ACTION_GATT_DISCONNECTED =
            "cn.com.nxyunzn,smart_parking_lock.le.ACTION_GATT_DISCONNECTED";
    public final static String ACTION_GATT_SERVICES_DISCOVERED =
            "cn.com.nxyunzn,smart_parking_lock.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE =
            "cn.com.nxyunzn,smart_parking_lock.le.ACTION_DATA_AVAILABLE";
    public final static String  EXTRA_DATA =
            "cn.com.nxyunzn,smart_parking_lock.le.EXTRA_DATA";
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothAddress;
    private BluetoothGatt mBluetoothGatt;
    private int mConnectionState = STATE_DISCONNECTED;

    private final static UUID UUID_HEART_RATE_MEASURMENT  = UUID.fromString(GattAttributes.HEART_RATE_MEASUREMENT);
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            if(newState == BluetoothProfile.STATE_CONNECTED){
                intentAction = ACTION_GATT_CONNECTED;
                mConnectionState = STATE_CONNECTED;
                broadcastUpdate(intentAction);
                Log.d(TAG,"Connect to GATT Server.");
                Log.d(TAG,"Attempting to start service discovery:"+mBluetoothGatt.discoverServices());
            }else if(newState == BluetoothProfile.STATE_DISCONNECTED){
                intentAction = ACTION_GATT_DISCONNECTED;
                mConnectionState = STATE_DISCONNECTED;
                Log.d(TAG,"Disconnected from Gatt server");
                broadcastUpdate(intentAction);
            }

        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if(status == BluetoothGatt.GATT_SUCCESS)
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            else
                Log.w(TAG,"onSerciesDiscovered:"+status);
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if(status == BluetoothGatt.GATT_SUCCESS)
                broadcastUpdate(ACTION_DATA_AVAILABLE,characteristic);
        }



        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE,characteristic);
        }
    };

    private void broadcastUpdate(final String action){
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }
    private void broadcastUpdate(final String action, final BluetoothGattCharacteristic characteristic){
        final Intent intent = new Intent(action);
        if(UUID_HEART_RATE_MEASURMENT.equals(characteristic.getUuid())){
            int flag = characteristic.getProperties();
            int format = -1;
            if((flag & 0x01) != 0) {
                format =  BluetoothGattCharacteristic.FORMAT_SINT16;
                Log.d(TAG,"Heart rate format Uint 16.");
            }else{
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
                Log.d(TAG,"Heart rate format Uint 8.");
            }
            final int heartRate = characteristic.getIntValue(format,1);
            Log.d(TAG,String.format("Received heart rate :%d",heartRate));
            intent.putExtra(EXTRA_DATA,String.valueOf(heartRate));
        }else {
            final byte[] data = characteristic.getValue();
            if(data != null && data.length > 0){
                final StringBuilder stringBuilder =  new StringBuilder(data.length);
                for (byte byteChar : data)
                    stringBuilder.append(String.format("%02X",byteChar));
                    intent.putExtra(EXTRA_DATA,new String(data)+"\n"+stringBuilder.toString());

            }
        }
        sendBroadcast(intent);
    }

    public class LocalBinder extends Binder {
        public BluetoothLeService getService(){
            return BluetoothLeService.this;
        }
    }
    private final IBinder mBinder = new LocalBinder();


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        close();
        return super.onUnbind(intent);
    }

    public boolean initialize(){
        if(mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if(mBluetoothManager == null)
                return false;
        }
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if(mBluetoothAdapter == null)
            return false;
        return true;
    }


    public boolean connect(final String address){
        if(mBluetoothAdapter == null || address == null)
            return false;
        if(mBluetoothAddress != null && address.equals(mBluetoothAddress)&& mBluetoothGatt != null){
            if(mBluetoothGatt.connect()){
                mConnectionState  = STATE_CONNECTING;
                return true;
            }else
                return false;
        }
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if(device == null)
            return false;
        mBluetoothGatt = device.connectGatt(this,false,mGattCallback);
        Log.d(TAG,"Trying to create a new connection");
        mBluetoothAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;
    }

    public void disconnect(){
        if(mBluetoothAdapter == null || mBluetoothGatt == null)
            return;
        mBluetoothGatt.disconnect();
    }
    public void close(){  // 关闭GATT服务
        if(mBluetoothGatt == null)
            return ;
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    public void readCharacteristic(BluetoothGattCharacteristic characteristic){
        if(mBluetoothAdapter == null || mBluetoothGatt == null)
            return ;
        Log.w(TAG,"read characteristic");
        mBluetoothGatt.readCharacteristic(characteristic);
    }
    public void writeCharacteristic(BluetoothGattCharacteristic characteristic,String commands){
        if(mBluetoothAdapter == null || mBluetoothGatt == null)
            return ;
        Log.w(TAG,"write characteristic");
        mBluetoothGatt.writeCharacteristic(characteristic);
    }

    public void setCharacteristicNotification(BluetoothGattCharacteristic characteristic,boolean enabled){
        if(mBluetoothAdapter == null || mBluetoothGatt == null)
            return;
        mBluetoothGatt.setCharacteristicNotification(characteristic,enabled);
        if(UUID_HEART_RATE_MEASURMENT.equals(characteristic.getUuid())){
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(GattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGatt.writeDescriptor(descriptor);
        }
    }


    public List<BluetoothGattService> getSupportedGattServices(){
        if(mBluetoothGatt == null )return null;
        return mBluetoothGatt.getServices();
    }
}
