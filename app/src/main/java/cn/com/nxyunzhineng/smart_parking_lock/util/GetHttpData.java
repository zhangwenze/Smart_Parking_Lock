package cn.com.nxyunzhineng.smart_parking_lock.util;

import android.app.Activity;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wenze on 2016/10/22.
 */

public class GetHttpData extends AsyncTask<String,Void,String> {


    public GetHttpData(Activity activity)
    {

    }
    @Override
    protected String doInBackground(String[] params) {
        try {
            return DownloadURL(params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "false";
    }
    private String DownloadURL(String my_url) throws IOException {
        InputStream is = null;
        int len = 8096;
        try {
            URL url = new URL(my_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.connect();
            int response_code = connection.getResponseCode();
            if(response_code == 200 || response_code == 300){
                is = connection.getInputStream();
            }
            String  re =  readIt(is,len);

            return re;
        } catch (MalformedURLException e ) {
            e.printStackTrace();
        }
        return null;
    }
    private  String readIt(InputStream inputStream,int len) throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(inputStream,"UTF-8");
        char [] buffer = new char[len];
        reader.read(buffer);

        return new String(buffer);
    }
}
