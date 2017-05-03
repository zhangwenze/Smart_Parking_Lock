package cn.com.nxyunzhineng.smart_parking_lock.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者：  wenze
 * 时间：  2017/4/29.
 * 版本：
 * 内容：
 */

public class Regex {
    private static String regex_phone = "^[1][34578][0-9]{9}$";

    public  static boolean IsPhone(String phone){
        Pattern pattern = Pattern.compile(regex_phone);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
