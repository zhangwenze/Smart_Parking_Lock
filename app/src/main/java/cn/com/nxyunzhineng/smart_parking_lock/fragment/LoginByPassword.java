package cn.com.nxyunzhineng.smart_parking_lock.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.com.nxyunzhineng.smart_parking_lock.R;
import cn.com.nxyunzhineng.smart_parking_lock.activity.LoginActivity;
import cn.com.nxyunzhineng.smart_parking_lock.activity.RegActivity;

/**
 * 作者：  wenze
 * 时间：  2017/4/27.
 * 版本：
 * 内容：
 */

public class LoginByPassword extends Fragment implements View.OnClickListener {

    private TextView regButton;
    private TextView findButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.loginbypassword,container,false);
        regButton = (TextView) view.findViewById(R.id.reg_user);
        regButton.setOnClickListener(this);
        return view;
    }

    public void DirectActivity(int id)
    {
        switch (id)
        {
            case 1:
                Intent intent = new Intent(this.getContext(),RegActivity.class);
                startActivity(intent);

                break;

        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.reg_user:
            DirectActivity(1);
            break;
        }
    }
}
