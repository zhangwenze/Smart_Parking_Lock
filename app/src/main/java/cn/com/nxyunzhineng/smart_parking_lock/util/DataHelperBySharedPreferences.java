package cn.com.nxyunzhineng.smart_parking_lock.util;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by wenze on 2017/2/8.
 */

public class DataHelperBySharedPreferences {

    private static DataHelperBySharedPreferences helper = new DataHelperBySharedPreferences();
    private static Context context;
    private DataHelperBySharedPreferences(){

    }
    public boolean writeData(String value,String key){
       SharedPreferences sharedPreferences = context.getSharedPreferences("smp_data",Context.MODE_PRIVATE);
       SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key,value);
        return editor.commit();
    }
    public String readData(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences("smp_data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return  sharedPreferences.getString(key,null);
    }
    public static DataHelperBySharedPreferences getInstance(Context contex){
        context = contex;
        return helper;
    }
}
