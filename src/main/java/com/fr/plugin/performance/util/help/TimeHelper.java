package com.fr.plugin.performance.util.help;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by yuwh on 2019/1/3
 * Description: 时间机器
 */
public class TimeHelper {
    private static HashMap<Integer, String> MOD_LIST = new HashMap<>();

    static{
        MOD_LIST.put(1, "yyyy-MM-dd HH:mm:ss");
        MOD_LIST.put(2, "yyyy-MM-dd");
        MOD_LIST.put(3, "HH:mm:ss");
        MOD_LIST.put(4, "HH:mm");
        MOD_LIST.put(5, "yyyy-w");
        MOD_LIST.put(6, "yyyy-MM-W");
        MOD_LIST.put(7, "MMM dd,yyyy");
    }

    private static String getSimpleFormat(Date date, String format){
        SimpleDateFormat sdf= new SimpleDateFormat(format, Locale.ENGLISH);
        return sdf.format(date);
    }

    public static String simpleFormat(Date date, int mod){
        return MOD_LIST.containsKey(mod)? getSimpleFormat(date, MOD_LIST.get(mod)): getSimpleFormat(date, MOD_LIST.get(1));
    }

    public static String simpleFormat(Date date, String format) {
        String result = "";
        try {
            result = getSimpleFormat(date, format);
        } catch(Exception e){
            result = getSimpleFormat(date, MOD_LIST.get(1));
            e.printStackTrace();
        } finally{
            return result;
        }
    }

    public static String simpleFormat(int mod){ return simpleFormat(new Date(), mod); }

    public static String simpleFormat(String format){ return simpleFormat(new Date(), format); }
}
