package cn.com.nxyunzhineng.smart_parking_lock.socket;


import android.os.Handler;

import java.net.Socket;

/**
 * Created by wenze on 2016/10/2.
 */

public   class TcpHelper{
    public static Socket clientSocket;          //建立TCP连接
    public static boolean isConnect;            //是否连接

    public Handler recv_Handler;
    public boolean SendCommand2Machine(String text){
        return false;
    }

}
