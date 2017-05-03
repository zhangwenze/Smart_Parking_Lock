package cn.com.nxyunzhineng.smart_parking_lock.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import cn.com.nxyunzhineng.smart_parking_lock.R;
import cn.com.nxyunzhineng.smart_parking_lock.activity.MainActivity;
import cn.com.nxyunzhineng.smart_parking_lock.adapter.LockList;
import cn.com.nxyunzhineng.smart_parking_lock.beans.LockItemBean;
import cn.com.nxyunzhineng.smart_parking_lock.view.HorizontalListView;
import cn.com.nxyunzhineng.smart_parking_lock.view.PowerCharts;


/**
 *
 */





public class MyLockFragment extends Fragment  implements  View.OnClickListener{

    public TextView title;
    public TextView detail;
    private Button upButton;
    private Button downButton;
    private Button pauseButton;
    public PowerCharts powerCharts;
    private HorizontalListView horizontalListView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.my_lock,container,false);
        upButton = (Button) view.findViewById(R.id.my_lock_up);
        upButton.setOnClickListener(this);
        downButton = (Button) view.findViewById(R.id.my_lock_down);
        downButton.setOnClickListener(this);
        pauseButton = (Button) view.findViewById(R.id.my_lock_pause);
        pauseButton.setOnClickListener(this);
        powerCharts = (PowerCharts) view.findViewById(R.id.power_charts);
        powerCharts.setOnClickListener(this);
        powerCharts.setProgress(80);
      //  mHorizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.sp_list);
        ArrayList<LockItemBean > ls = new ArrayList<LockItemBean>();
        LockItemBean la = new LockItemBean();
        la.setmLockName("wenze");
        la.setmLockState("now");
        ls.add(la);
        ListAdapter adapter = new LockList(this.getActivity(),ls);
        horizontalListView = (HorizontalListView) view.findViewById(R.id.sp_list);
        horizontalListView.setAdapter(adapter);
        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.my_lock_up:
                MainActivity.actionUp();
                this.Show("up");
                break;
            case R.id.my_lock_down:
                MainActivity.actionDown();
                this.Show("down");
                break;
            case R.id.my_lock_pause:
                this.Show("pause");
                break;
            case R.id.sp_list:
                this.Show("HorizontalScrollView is clicked !");
            default:
                break;
        }

    }
    public void Show(String string){
        Toast.makeText(this.getContext(),string,Toast.LENGTH_LONG).show();
    }


}
