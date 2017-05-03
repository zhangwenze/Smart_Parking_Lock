package cn.com.nxyunzhineng.smart_parking_lock.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.com.nxyunzhineng.smart_parking_lock.R;
import cn.com.nxyunzhineng.smart_parking_lock.util.RegUser;
import cn.com.nxyunzhineng.smart_parking_lock.util.Regex;
import cn.com.nxyunzhineng.smart_parking_lock.util.URLPath;

public class RegActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mGetCode;
    private TextView userphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.reg_toolbar);
        //ActionBar actionBar = this.getSupportActionBar();
       // actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setTitle("");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayShowCustomEnabled(true);
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP);
        // this.getSupportActionBar().setHomeButtonEnabled(true);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeButtonEnabled(true);
        TextView reg_title = (TextView) findViewById(R.id.reg_title);
        userphone = (TextView) findViewById(R.id.user_phone);
        reg_title.setText("注册新用户");
        mGetCode = (Button) findViewById(R.id.get_code);
        mGetCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_code:
                RegUser reguer = new RegUser(this);
                String userphone_s = userphone.getText().toString();
                if(userphone_s.equals("")) {
                    Log.e("reg:","---------------------->click");
                    Toast.makeText(this, "NULL", Toast.LENGTH_LONG).show();

                }else {
                    if(Regex.IsPhone(userphone_s))
                    reguer.execute(URLPath.USERREG + userphone.getText());
                    else {
                        Toast.makeText(this, "请填写正确的手机号码", Toast.LENGTH_LONG).show();
                        userphone.setText("");
                    }
                }

                break;

        }
    }

    public synchronized  void  changeState(){

    }




}
