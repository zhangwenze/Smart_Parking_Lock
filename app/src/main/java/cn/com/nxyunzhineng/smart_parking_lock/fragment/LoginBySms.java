package cn.com.nxyunzhineng.smart_parking_lock.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.com.nxyunzhineng.smart_parking_lock.R;

/**
 * 作者：  wenze
 * 时间：  2017/4/27.
 * 版本：
 * 内容：
 */

public class LoginBySms extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.loginbysms,container,false);
        return view;
    }
}
