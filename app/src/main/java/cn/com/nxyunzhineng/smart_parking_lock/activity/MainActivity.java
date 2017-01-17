package cn.com.nxyunzhineng.smart_parking_lock.activity;


import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/***
 * 智能车位锁的主界面类
 * @作者  文泽
 * @时间  2016-9-27
 * @版本  0
 * @版权 宁夏云智能科技开发公司
 */
import cn.com.nxyunzhineng.smart_parking_lock.fragment.AMapFragment;
import cn.com.nxyunzhineng.smart_parking_lock.fragment.CarLifeFragment;
import cn.com.nxyunzhineng.smart_parking_lock.fragment.MyLockFragment;
import cn.com.nxyunzhineng.smart_parking_lock.R;
import cn.com.nxyunzhineng.smart_parking_lock.fragment.SelfRoomFragment;
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


    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;

    private static final long SCAN_PERIOD = 2000;

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
            Snackbar snackbar = Snackbar.make(toolbar,string,Snackbar.LENGTH_LONG);
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


                break;
            case R.id.action_settings:
                startActivity(new Intent(this,SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onClick(View v) {

        this.reset();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HideFragment(fragmentTransaction);
        Log.d("show data:",v.toString());
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
        public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    };
    private void scanLeDevice(final boolean enable){
        if(enable){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;

                }
            },SCAN_PERIOD);
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        }else
        {
            mScanning = false;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }
}
