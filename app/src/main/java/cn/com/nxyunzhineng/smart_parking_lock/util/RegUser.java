package cn.com.nxyunzhineng.smart_parking_lock.util;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 作者：  wenze
 * 时间：  2017/4/29.
 * 版本：
 * 内容：
 */

public class RegUser extends GetHttpData {
    private Activity activity;
    private int  isexist;
    public RegUser(Activity activity) {
        super(activity);
        this.activity = activity;
    }
    @Override
    protected void onPostExecute(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            isexist = jsonObject.getInt("isexist");
            switch(isexist){
                case 2:    Toast.makeText(activity,"手机号码已存在",Toast.LENGTH_LONG).show();
                break;
                case 0:  Toast.makeText(activity,"短信已发送，请注意查收",Toast.LENGTH_LONG).show();
                    break;
                case 1: Toast.makeText(activity,"参数错误",Toast.LENGTH_LONG).show();
                    break;

            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(activity,"访问服务器异常",Toast.LENGTH_SHORT).show();
        }

        super.onPostExecute(s);
    }
}
