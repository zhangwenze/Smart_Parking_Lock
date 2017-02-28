package cn.com.nxyunzhineng.smart_parking_lock.service;

/**
 * Created by wenze on 2016/11/26.
 */
import java.util.HashMap;

public class GattAttributes {




        private static HashMap<String, String> attributes = new HashMap();
        public static String HEART_RATE_MEASUREMENT = "00002a37-0000-1000-8000-00805f9b34fb";
        public static String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";

        static {
            // Sample Services.
            attributes.put("00001800-0000-1000-8000-00805f9b34fb", "Heart Rate Service");
            attributes.put("00001801-0000-1000-8000-00805f9b34fb", "Device Information Service");
            // Sample Characteristics.
            attributes.put(HEART_RATE_MEASUREMENT, "Heart Rate Measurement");
            attributes.put("00002a29-0000-1000-8000-00805f9b34fb", "Manufacturer Name String");
            attributes.put("0000fff6-0000-1000-8000-00805f9b34fb","Smart_Parking_Lock");
        }

        public static String lookup(String uuid, String defaultName) {
            String name = attributes.get(uuid);
            return name == null ? defaultName : name;
        }

}
