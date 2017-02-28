package cn.com.nxyunzhineng.smart_parking_lock.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.nxyunzhineng.smart_parking_lock.R;
import cn.com.nxyunzhineng.smart_parking_lock.activity.LoginActivity;
import cn.com.nxyunzhineng.smart_parking_lock.util.UserData;
import cn.com.nxyunzhineng.smart_parking_lock.view.UserHeader;


public class SelfRoomFragment extends Fragment implements  UserHeader.OnTurnListener,View.OnClickListener{


    private UserHeader userHeader;
    private ImageView imageView;
    private RelativeLayout mUserInfo;
    private Bundle mbundle;
    private TextView username;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // return super.onCreateView(inflater, container, savedInstanceState);
        View  view = inflater.inflate(R.layout.fragment_self,null);
        userHeader = (UserHeader) view.findViewById(R.id.user_head);
        imageView = (ImageView) view.findViewById(R.id.background_img);
        userHeader.setHeader(imageView);
        userHeader.setOnTurnListener(this);
        mUserInfo = (RelativeLayout) view.findViewById(R.id.user_info);
        mUserInfo.setOnClickListener(this);

       username = (TextView) view.findViewById(R.id.user_name);


        return view;

    }

    @Override
    public void onTurn() {

    }
    private void goLogin(){
        Intent intent  = new Intent(this.getActivity(), LoginActivity.class);
        intent.putExtra("username",mbundle);
        startActivity(intent);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_info:

                goLogin();

                break;

        }
    }

    @Override
    public void onResume() {


        UserData data = (UserData) this.getActivity().getApplication();
        if(data.getmName()!= null)
            username.setText(data.getmName());

        Log.e(this.toString(),"on resume ");
        super.onResume();
    }
}
