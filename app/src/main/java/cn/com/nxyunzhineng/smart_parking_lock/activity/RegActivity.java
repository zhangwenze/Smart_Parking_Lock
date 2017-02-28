package cn.com.nxyunzhineng.smart_parking_lock.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.util.AsyncListUtil;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.com.nxyunzhineng.smart_parking_lock.R;
import cn.com.nxyunzhineng.smart_parking_lock.util.URLPath;

public class RegActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mGetCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //ActionBar actionBar = this.getSupportActionBar();
       // actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setTitle("");
        setSupportActionBar(toolbar);
        mGetCode = (Button) findViewById(R.id.get_code);
        mGetCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_code:
                getCode();
                break;

        }
    }

    public synchronized  void  changeState(){

    }

    private void getCode(){
        GetCode getCode = new GetCode();
        getCode.execute(URLPath.GETCODE);

    }

    private class  GetCode extends AsyncTask<String,Void,Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            HttpURLConnection httpURLConnection;
            try {
                URL url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.connect();

            } catch (MalformedURLException e) {
                return false;
            } catch (IOException e) {
                return false;
            }
            PrintWriter pw = null;

            try {

                pw = new PrintWriter(httpURLConnection.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            pw.write("phone=15595287431");
            pw.flush();
            pw.close();
            try {
                int code = httpURLConnection.getResponseCode();
                Log.d("Connection Code",code+"");
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d("get COde ","successful");
            return true;
        }
    }
}
