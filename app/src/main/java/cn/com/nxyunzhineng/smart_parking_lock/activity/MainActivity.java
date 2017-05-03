package cn.com.nxyunzhineng.smart_parking_lock.activity;


import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
/***
 * 智能车位锁的主界面类
 * @作者  文泽
 * @时间  2016-9-27
 * @版本  0
 * @版权 宁夏云智能科技开发公司
 */
import java.io.UnsupportedEncodingException;
import java.nio.BufferOverflowException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import cn.com.nxyunzhineng.smart_parking_lock.adapter.LeDeviceListAdapter;
import cn.com.nxyunzhineng.smart_parking_lock.fragment.AMapFragment;
import cn.com.nxyunzhineng.smart_parking_lock.fragment.CarLifeFragment;
import cn.com.nxyunzhineng.smart_parking_lock.fragment.MyLockFragment;
import cn.com.nxyunzhineng.smart_parking_lock.R;
import cn.com.nxyunzhineng.smart_parking_lock.fragment.SelfRoomFragment;
import cn.com.nxyunzhineng.smart_parking_lock.service.BluetoothLeService;
import cn.com.nxyunzhineng.smart_parking_lock.util.URLPath;
import cn.com.nxyunzhineng.smart_parking_lock.util.UpdateUtil;


public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    private TextView my_lock_text;              //
    private LinearLayout my_lock_layout;        //
    private ImageView my_lock_img;              //

    private TextView around_lock_text;          //
    private LinearLayout around_lock_layout;    //
    private ImageView around_lock_img;          //

    private TextView my_friends_text;           //
    private LinearLayout my_friends_layout;     //
    private ImageView my_friends_img;           //

    private TextView car_life_text;             //
    private LinearLayout car_life_layout;       //
    private ImageView car_life_img;             //



    private Button retry;
    private TextView title;                     //标题
    private MyLockFragment   mylock_fragment;     //我的车位锁Fragment界面
    private AMapFragment     map_fragment;
    private FragmentManager fragmentManager;    //Fragment 界面管理器，用来存储或显示Fragment
    private SelfRoomFragment selfRoomFragment;
    private CarLifeFragment carLifeFragment;
    private Menu menu;
    private int press_color ;
    private int nomarl_color;
    private  Toolbar toolbar;
    public static final int REQUEST_ENABLE_BLE = -1;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter ;  //蓝牙适配器
    private LeDeviceListAdapter mLeDeviceListAdapter;
    private boolean mScanning = false;
    private Handler mHandler;
    private AlertDialog mScanningDialog;
    private AlertDialog mBleDeviceDialog;
    private AlertDialog mNullDeviceDialog;
    private static final long SCAN_PERIOD = 5000;
    private static BluetoothLeService mBluetoothLeService;
    private static List<BluetoothGattService> gattServices;
    private static boolean mConnected = false;
    private String mDeviceAddress;
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
              mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if(!mBluetoothLeService.initialize()){
                Log.e("error:","Unable to init Bluetooth");
                finish();
            }
            mBluetoothLeService.connect(mDeviceAddress);
            Log.w("ble !!!!!","successful");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBluetoothLeService = null;
        }
    };
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if(BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)){
                mConnected = true;
            }else if(BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action))
                mConnected = false;
            else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {

            }
            else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)){

            }
        }
    };
    private  void setViews(){
        my_lock_layout = (LinearLayout) findViewById(R.id.my_lock_layout);
        my_lock_text = (TextView) findViewById(R.id.my_lock_text);
        my_lock_img  = (ImageView) findViewById(R.id.my_lock_img);
        around_lock_img = (ImageView) findViewById(R.id.arounds_lock_img);
        around_lock_layout = (LinearLayout) findViewById(R.id.arounds_lock_layout);
        around_lock_text = (TextView) findViewById(R.id.around_lock_text);
        my_friends_img = (ImageView) findViewById(R.id.life_img);
        my_friends_layout = (LinearLayout) findViewById(R.id.life_layout);
        my_friends_text = (TextView) findViewById(R.id.life_text);
        car_life_img = (ImageView) findViewById(R.id.car_life_img);
        car_life_layout = (LinearLayout) findViewById(R.id.car_life_layout);
        car_life_text = (TextView) findViewById(R.id.car_life_text);
        press_color = Color.argb(0xff,0x12,0x96,0xdb);
        nomarl_color = Color.argb(0xff,0x51,0x51,0x51);

        reset();
    }

    private void setClickListeners(){
        my_lock_layout.setOnClickListener(this);
        my_friends_layout.setOnClickListener(this);
        car_life_layout.setOnClickListener(this);
        around_lock_layout.setOnClickListener(this);

    }

    private void isSupportBLE(){
        if(!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)){
           String string = "蓝牙不支持，请更换android4.3及以上的智能手机";
            Snackbar.make(toolbar,string,Snackbar.LENGTH_LONG).show();
        }else{
            String string = "蓝牙支持";
          //  Snackbar.make(toolbar,string,Snackbar.LENGTH_LONG).getView().setBackgroundColor(0xFF0;
            Snackbar snackbar = Snackbar.make(toolbar,string,Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(Color.RED);
            snackbar.show();
            this.openBLE();
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void openBLE(){
        BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        if(bluetoothAdapter == null || !bluetoothAdapter.isEnabled()){
            Intent enable = new Intent (BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enable,REQUEST_ENABLE_BLE);
        }else
        {
            String string = "蓝牙已经打开";
            //  Snackbar.make(toolbar,string,Snackbar.LENGTH_LONG).getView().setBackgroundColor(0xFF0;
            Snackbar snackbar = Snackbar.make(toolbar,string,Snackbar.LENGTH_SHORT);
            snackbar.getView().setBackgroundColor(Color.DKGRAY);
            snackbar.show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        title = (TextView) findViewById(R.id.title);
        title.setText("我的车位锁");
        this.setViews();
        mHandler = new Handler();
        this.isSupportBLE();
        this.setClickListeners();
        UpdateUtil lg = new UpdateUtil(MainActivity.this);
        lg.execute(URLPath.GETVERSION);
        mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        mLeDeviceListAdapter = new LeDeviceListAdapter(this);
        mScanningDialog = new AlertDialog.Builder(this).setView(R.layout.scan_dialog).create();
        mScanningDialog.setCanceledOnTouchOutside(false);
        Intent gattServiceIntent = new Intent(MainActivity.this,BluetoothLeService.class);
        bindService(gattServiceIntent,mServiceConnection,BIND_AUTO_CREATE);
        mBleDeviceDialog = new AlertDialog.Builder(this).setAdapter(mLeDeviceListAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String address = ((BluetoothDevice)mLeDeviceListAdapter.getItem(which)).getAddress();
                Log.w("data",address);
                mDeviceAddress = address;

                mBluetoothLeService.connect(address);
                gattServices = mBluetoothLeService.getSupportedGattServices();
            }
        }).create();
        mBleDeviceDialog.setCanceledOnTouchOutside(false);
        mNullDeviceDialog = new AlertDialog.Builder(this).create();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        my_lock_layout.performClick();
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_search:
                scanLeDevice(true);
                break;
            case R.id.action_settings:
                startActivity(new Intent(this,SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mGattUpdateReceiver,makeGattUpdateIntentFilter());
        if(mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
            Log.d("sssssss","connect request result"+result);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mGattUpdateReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
        mBluetoothLeService = null;
    }

    private static IntentFilter makeGattUpdateIntentFilter(){
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        return intentFilter;
    }
    @Override
    public void onClick(View v) {

        this.reset();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HideFragment(fragmentTransaction);
        switch (v.getId()){
            case R.id.my_lock_layout:
                title.setText("我的车位锁");
                menu.clear();
                getMenuInflater().inflate(R.menu.main, menu);
                my_lock_img.setImageResource(R.drawable.ic_home_press);
                my_lock_text.setTextColor(press_color);
                    if(mylock_fragment == null)
                    {
                        mylock_fragment = new MyLockFragment();
                        fragmentTransaction.add(R.id.main_fragment,mylock_fragment);
                    }else
                        fragmentTransaction.show(mylock_fragment);
                break;
            case R.id.arounds_lock_layout:
                title.setText("周边车位锁");
                menu.clear();
                getMenuInflater().inflate(R.menu.arounds_locl_menu, menu);
                around_lock_text.setTextColor(press_color);
                around_lock_img.setImageResource(R.drawable.ic_location_press);
                if(map_fragment == null){
                    map_fragment = new AMapFragment();
                    fragmentTransaction.add(R.id.main_fragment,map_fragment);
                }else
                    fragmentTransaction.show(map_fragment);
                break;
            case R.id.car_life_layout:
                title.setText("个人中心");
                menu.clear();
                car_life_text.setTextColor(press_color);
                car_life_img.setImageResource(R.drawable.ic_me_press);
                if(selfRoomFragment == null){
                    selfRoomFragment = new SelfRoomFragment();
                    fragmentTransaction.add(R.id.main_fragment,selfRoomFragment);
                }else
                    fragmentTransaction.show(selfRoomFragment);
                break;
            case R.id.life_layout:
                title.setText("生活周边");
                menu.clear();
                my_friends_text.setTextColor(press_color);
                my_friends_img.setImageResource(R.drawable.ic_life_press);
                if(carLifeFragment == null){
                    carLifeFragment = new CarLifeFragment();
                    fragmentTransaction.add(R.id.main_fragment,carLifeFragment);
                }else
                    fragmentTransaction.show(carLifeFragment);
                break;
        }
        fragmentTransaction.commit();
    }
    private void reset(){
        my_lock_img.setImageResource(R.drawable.ic_home);
        my_lock_text.setTextColor(nomarl_color);
        my_friends_text.setTextColor(nomarl_color);
        my_friends_img.setImageResource(R.drawable.ic_life);
        around_lock_text.setTextColor(nomarl_color);
        around_lock_img.setImageResource(R.drawable.ic_location);
        car_life_text.setTextColor(nomarl_color);
        car_life_img.setImageResource(R.drawable.ic_me);
    }
    private void HideFragment(FragmentTransaction ft){
        if(mylock_fragment != null)
            ft.hide(mylock_fragment);
        if(map_fragment != null){
            ft.hide(map_fragment);
        }
        if(selfRoomFragment!=null)
            ft.hide(selfRoomFragment);
        if(carLifeFragment != null)
            ft.hide(carLifeFragment);
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLeDeviceListAdapter.addDevice(device);
                    Log.e("device:",device.getAddress()+"\n"+device.getName());
                    mLeDeviceListAdapter.notifyDataSetChanged();
                }
            });
        }
    };
    private void scanLeDevice(final boolean enable){
        if(enable){

            mScanningDialog.show();
            mNullDeviceDialog.hide();
            mBleDeviceDialog.hide();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    mScanningDialog.hide();
                    if(!mLeDeviceListAdapter.isEmpty()){
                        mBleDeviceDialog.show();

                    }
                    else{
                        mNullDeviceDialog.show();
                        Window window =  mNullDeviceDialog.getWindow();
                        window.setContentView(R.layout.ble_device_item_null);
                        retry = (Button)window.findViewById(R.id.retry);
                        retry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                scanLeDevice(true);
                            }
                        });
                    }
                }
            },SCAN_PERIOD);
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);

        }else
        {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }

    private long exitTime = 0; //第一次点击回退键计时
    @Override
    /**
     * 回退事件
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    public static boolean actionUp(){
        if(gattServices == null) {
            Log.e("fail to get service","code 1");
            return false;
        }
        final String uuid = "0000fff6-0000-1000-8000-00805f9b34fb";
          BluetoothGattService targetServive = null;
          BluetoothGattCharacteristic targetCharacteristic = null;
            for (BluetoothGattService gattService : gattServices){
                Log.d("UUID",gattService.getUuid().toString());
                List<BluetoothGattCharacteristic> ls ;
                ls=gattService.getCharacteristics();
                for(BluetoothGattCharacteristic characteristic : ls){
                    Log.w("characteristic:",characteristic.getUuid().toString());
                    if(characteristic.getUuid().toString().equals(uuid)){
                        targetCharacteristic = characteristic;
                    }
                }
                targetServive = gattService;
            }
        if(targetServive == null)
            return false;
        if(targetCharacteristic == null)
            return false;
        String command = "up12345678";
        try {
            targetCharacteristic.setValue(command.getBytes("ascii"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mBluetoothLeService.writeCharacteristic(targetCharacteristic,null);
        mBluetoothLeService.readCharacteristic(targetCharacteristic);
        try {
            Log.d("characteristic:",new String(targetCharacteristic.getValue(),"ascii"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Log.d("TARGET UUID",targetServive.getUuid().toString());

        return true;
    }
}
