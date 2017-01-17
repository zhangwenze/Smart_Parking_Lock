package cn.com.nxyunzhineng.smart_parking_lock.util;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by wenze on 2016/10/23.
 */

public class AppUpdateInstall extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("receiver:","Ok");
        if(intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
            AlertDialog alertDialog = new AlertDialog.Builder(context)
                    .setTitle("软件更新")
                    .setMessage("新版本已下载完成是否安装？").setNegativeButton("安装",null)
                    .setPositiveButton("取消",null).show();
        }

    }

}
