package cn.com.nxyunzhineng.smart_parking_lock.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import cn.com.nxyunzhineng.smart_parking_lock.R;
import cn.com.nxyunzhineng.smart_parking_lock.util.UserData;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mRegUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // this.getActionBar().setDisplayHomeAsUpEnabled(true);
        mRegUser = (TextView) findViewById(R.id.reg_user);
        mRegUser.setOnClickListener(this);
        UserData data = (UserData) this.getApplication();
        data.setmName("文泽");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.reg_user:
                Intent intent = new Intent();
                intent.setClass(this,RegActivity.class);
                startActivity(intent);
                this.finish();
                break;
        }
    }
}
