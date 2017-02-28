package cn.com.nxyunzhineng.smart_parking_lock.activity;

/*
 * 首页
 * 跳转到欢迎界面或者直接进入主界面
 */



import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import cn.com.nxyunzhineng.smart_parking_lock.R;
import cn.com.nxyunzhineng.smart_parking_lock.util.DataHelperBySharedPreferences;

public class FristActivity extends Activity {



    private AlphaAnimation animation;
    private TextView  textView;
    String mPermission[] = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_WIFI_STATE
    };
    private boolean state  = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frist);
        final DataHelperBySharedPreferences dataHelperBySharedPreferences = DataHelperBySharedPreferences.getInstance(this);
        String value = dataHelperBySharedPreferences.readData("FristUse2");
        if(value == null)
            dataHelperBySharedPreferences.writeData("true","FristUse2");
        else
            state = true;


        animation = new AlphaAnimation(1.0f,1.0f);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                if(state)
                    goMain();
                else
                    goWelcome();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        textView = (TextView) findViewById(R.id.app_name);
        animation.setDuration(1500);
        checkPermissions();

    }
    private void goWelcome(){
        Intent intent = new Intent(this,WelcomeActivity.class);
        startActivity(intent);
        this.finish();
    }
    private void goMain(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        this.finish();
    }
    private void checkPermissions(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for(int i = 0; i < mPermission.length;i++)
            {
                if(checkSelfPermission(mPermission[i]) != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(mPermission,1);
                    return;
                }
            }
            animation.start();
            textView.setAnimation(animation);
        }
    }

    /**
     * 申请权限的回调方法
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        animation.start();
        textView.setAnimation(animation);
    }
}
