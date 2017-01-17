package cn.com.nxyunzhineng.smart_parking_lock.util;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by wenze on 2016/10/8.
 */

public class ScanParking_Lock {

    public List<ScanResult> ScanParK_Lock_List(Context context){

        List<ScanResult> scanResult_list;

        WifiManager wifiManager;
         wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        wifiManager.startScan();
        scanResult_list = wifiManager.getScanResults();
        return scanResult_list;
    }
    public void EnableWifi(Context context){
        WifiManager wifiManager;
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }

    }
    public boolean Connect2PakingLock(String SSID, String pass, Context context){

        WifiManager wifiManager;
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID="\""+SSID+"\"";
        wifiConfiguration.preSharedKey="\""+pass+"\"";
        wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
        wifiConfiguration.hiddenSSID = false;
        wifiManager.enableNetwork(wifiManager.addNetwork(wifiConfiguration),true);


        return false;
    }
}
