package cn.com.nxyunzhineng.smart_parking_lock.util;

import android.app.Application;

/**
 * 作者：  wenze
 * 时间：  2017/2/23.
 * 版本：
 * 内容： 实现数据共享
 */

public class UserData extends Application {

    private String mName;
    private int is;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void setmName(String name){
        this.mName = name;
    }
    public String getmName(){
        return mName;
    }
    public void setIs(int i){this.is = i;};
    public int getIs(){return is;};
}
