package cn.com.nxyunzhineng.smart_parking_lock.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;

import cn.com.nxyunzhineng.smart_parking_lock.R;
import cn.com.nxyunzhineng.smart_parking_lock.activity.MainActivity;
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
                this.Show("down");
                break;
            case R.id.my_lock_pause:
                this.Show("pause");
                break;
            default:
                break;
        }

    }
    public void Show(String string){
        Toast.makeText(this.getContext(),string,Toast.LENGTH_LONG).show();
    }
}
