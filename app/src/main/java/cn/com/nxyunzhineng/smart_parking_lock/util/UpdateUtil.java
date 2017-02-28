package cn.com.nxyunzhineng.smart_parking_lock.util;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.os.CancellationSignal;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.net.URI;

/**
 * Created by wenze on 2016/10/22.
 */

public class UpdateUtil extends GetHttpData {
    private  Activity activity;
    private String versionName;
    public UpdateUtil(Activity activity) {
        super(activity);
        this.activity = activity;
    }


    @Override
    protected void onPostExecute(String s) {
        if(s.indexOf("false")>0){
        }else
        {
            try {
                JSONObject jsonObject = new JSONObject(s);
                versionName = jsonObject.getString("version");
                String nowversion;
                nowversion = getVersionName();
                Log.d("nowverion",nowversion);
                Log.d("versionName",versionName);

                if(!nowversion.equals(versionName)){
                    final String down_url = jsonObject.getString("url");
                    down_url.replace("\\/","/");

                    AlertDialog alertDialog = new AlertDialog.Builder(activity)
                            .setTitle("发现新版本")
                            .setMessage("更新内容：\n"+jsonObject.getString("context")+"\n或访问"+down_url+"下载最新版本")
                            .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(down_url));
                                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
                                    request.setTitle("正在下载");
                                    request.setDescription("云车位正在下载");
                                    request.setAllowedOverMetered(true);
                                    request.setAllowedOverRoaming(false);
                                    request.setDestinationInExternalFilesDir(activity, Environment.DIRECTORY_DOWNLOADS,"update.apk");
                                    request.setVisibleInDownloadsUi(true);
                                    request.setMimeType("application/vnd.android.package-archive");
                                    DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
                                    long id = downloadManager.enqueue(request);

                                }
                            }).setNegativeButton("下次更新",null).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    public  String getVersionName() throws PackageManager.NameNotFoundException {
        PackageManager packageManager = activity.getPackageManager();

        PackageInfo packageInfo = packageManager.getPackageInfo(activity.getPackageName(),0);
       // packageInfo.versionName = "2";
        return packageInfo.versionName;
    }

}
