package cn.com.nxyunzhineng.smart_parking_lock.beans;

/**
 * Created by wenze on 2016/9/29.
 */

public class MacSSID {
    private  int        signal_quality; // 信号质量
    private  String     ssid;

    public int getSignal_quality() {
        return signal_quality;
    }

    public void setSignal_quality(int signal_quality) {
        this.signal_quality = signal_quality;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
}
