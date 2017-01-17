package cn.com.nxyunzhineng.smart_parking_lock.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.UUID;

/**
 * Created by wenze on 2016/11/25.
 */

public class BluetoothLeService extends Service {

    private final static String TAG = BluetoothLeService.class.getSimpleName();

    /**
     * @deprecated    BLE  连接状态
     */
    private static final int STATE_DISCONNECTED = 0;   //未连接
    private static final int STATE_CONNECTING = 1;     //连接中
    private static final int STATE_CONNECTED = 2;      //已经连接
    /**
     * @deprecated   BLE广播消息
     */
    private final static String ACTION_GATT_CONNECTED =
            "cn.com.nxyunzn,smart_parking_lock.le.ACTION_GATT_CONNECTED";

    private final static String ACTION_GATT_DISCONNECTED =
            "cn.com.nxyunzn,smart_parking_lock.le.ACTION_GATT_DISCONNECTED";

    private final static String ACTION_GATT_SERVICES_DISCOVERED =
            "cn.com.nxyunzn,smart_parking_lock.le.ACTION_GATT_SERVICES_DISCOVERED";

    private final static String ACTION_DATA_AVAILABLE =
            "cn.com.nxyunzn,smart_parking_lock.le.ACTION_DATA_AVAILABLE";

    private final static String  EXTRA_DATA =
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
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }
    };

    private void broadcastUpdate(final String action){
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }
    private void broadcastUpdate(final String action, final BluetoothGattCharacteristic characteristic){
        final Intent intent = new Intent(action);
        //if(UUID_HEART)
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
