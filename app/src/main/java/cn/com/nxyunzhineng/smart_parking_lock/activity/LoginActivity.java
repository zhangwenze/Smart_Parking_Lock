package cn.com.nxyunzhineng.smart_parking_lock.activity;

import android.content.Intent;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.nxyunzhineng.smart_parking_lock.R;
import cn.com.nxyunzhineng.smart_parking_lock.fragment.LoginByPassword;
import cn.com.nxyunzhineng.smart_parking_lock.fragment.LoginBySms;
import cn.com.nxyunzhineng.smart_parking_lock.util.UserData;

public class LoginActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener,GestureDetector.OnGestureListener{

//    private TextView mRegUser;
    private FragmentManager fragmentManager ;
    private LoginByPassword loginByPassword;
    private LoginBySms loginBySms;

    private TabItem item_pass;
    private TabItem item_sms;
    private TabLayout choose_login;
    private GestureDetector detector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        this.getActionBar().setDisplayHomeAsUpEnabled(true);
        Toolbar loginToolbar = (Toolbar) findViewById(R.id.login_toolbar);
        loginToolbar.setTitle("");
        //loginToolbar.
        setSupportActionBar(loginToolbar);//ActionBar(loginToolbar);
       // this.getSupportActionBar().setDisplayShowTitleEnabled(false);
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
       // this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);


      //  this.getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
       // this.getSupportActionBar().setHomeButtonEnabled(true);



        choose_login = (TabLayout) findViewById(R.id.choose_login);
      //  choose_login.addOnTabSelectedListener(this);
        choose_login.addOnTabSelectedListener(this);
        TextView title = (TextView) findViewById(R.id.login_title);
        title.setText("登  陆");
        choose_login.performClick();

        //mRegUser = (TextView) findViewById(R.id.reg_user);
//        mRegUser.setOnClickListener(this);
       // this.getActionBar().setTitle("登陆");
        fragmentManager = this.getSupportFragmentManager();



        //fragmentTransaction.show(loginByPassword);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(loginByPassword==null) {
            loginByPassword = new LoginByPassword();
            fragmentTransaction.add(R.id.login_type,loginByPassword);
            fragmentTransaction.commit();
        }
        detector = new GestureDetector(this.getBaseContext(),this);

        UserData data = (UserData) this.getApplication();
        data.setmName("文泽");
        data.setIs(1);
    }



    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        switch (tab.getPosition())
        {
            case 0:
            {
                Log.e("anti","----------------------------ok");
                if(loginBySms!=null)
                    fragmentTransaction.hide(loginBySms);
                if(loginByPassword==null) {
                    loginByPassword = new LoginByPassword();
                    fragmentTransaction.add(R.id.login_type,loginByPassword);
                }else
                    fragmentTransaction.show(loginByPassword);
                fragmentTransaction.commit();
            }
            break;
            case 1:
            {
                Log.e("anti","----------------------------ok");
                if(loginByPassword!=null)
                fragmentTransaction.hide(loginByPassword);
                if(loginBySms==null) {
                    loginBySms = new LoginBySms();
                    fragmentTransaction.add(R.id.login_type,loginBySms);
                }else
                    fragmentTransaction.show(loginBySms);
                fragmentTransaction.commit();
            }
            break;

        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d("scroll:","---------------sk");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d("scroll:","---------------sk");
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
        Log.d("scroll:","---------------sk");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.detector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
