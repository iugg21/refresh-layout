package com.ctrun.view.cateye.refresh.util;

import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Locale;

/**
 * Created by Administrator on 2017/2/10.
 */

public class StringUtils {

    public static String stringForTime(long timeMs) {
        if (timeMs <= 0 || timeMs >= 24 * 60 * 60 * 1000) {
            return "00:00";
        }
        long totalSeconds = timeMs / 1000;
        int seconds = (int) (totalSeconds % 60);
        int minutes = (int) ((totalSeconds / 60) % 60);
        int hours = (int) (totalSeconds / 3600);
        StringBuilder stringBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(stringBuilder, Locale.getDefault());
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    public static String changeNumToString(int num) {
        if (num >= 10000) {
            if (num % 10000 == 0) {
                return String.format("%sw", num / 10000);
            }

            return String.format("%sw+", num / 10000);
        }

        return String.format("%s", num);
    }

    public static String changeNumToCN4(int num) {
        if (num >= 100000) {
            DecimalFormat format = new DecimalFormat("0.0");
            return String.format("%s万", format.format(num / 10000.0));
        } else {
            return String.format("%s", num);
        }
    }

    public static String changeNumToCN3(int num) {
        if (num >= 1000000) {
            DecimalFormat format = new DecimalFormat("0.0");
            return String.format("%s万", format.format(num / 10000.0));
        } else {
            return String.format("%s", num);
        }
    }

    public static String changeNumToCN2(int num) {
        if (num >= 10000) {
            DecimalFormat format = new DecimalFormat("0.0");
            return String.format("%s万", format.format(num / 10000.0));
        } else {
            return String.format("%s", num);
        }
    }

    /**
     * 将数字转为带单位(万)
     *
     * @param num
     * @return
     */
    public static String changeNumToCN(int num) {
        DecimalFormat format = new DecimalFormat("0.0");
        int u = (int) Math.floor(Math.log10(num));
        if (u >= 5) {
            return String.format("%s万", format.format(num / 10000.0));
        } else {
            return String.format("%s", num);
        }
    }


    /**
     * 讲数字转为带单位(亿)
     *
     * @param count
     * @return
     */
    public static String changeMillionIntoBillion(int count) {
        DecimalFormat format = new DecimalFormat("0.0");
        int u = (int) Math.floor(Math.log10(count));
        if (u >= 4) {
            return String.format("%s亿", format.format(count / 10000.0));
        } else {
            return String.format("%s万", count);
        }
    }

    public static String[] changeMillionIntoBillion2(int count) {
        DecimalFormat format = new DecimalFormat("0.00");
        int u = (int) Math.floor(Math.log10(count));
        if (u >= 4) {
            return new String[] {String.format("%s", format.format(count / 10000.0)), "亿"};
        } else {
            return new String[] {String.format("%s", count), "万"};
        }
    }

    public static String getRealUrl(String url) {
        if (url.contains("id=")) {
            if(url.contains("&")) {
                String id = url.substring(url.indexOf("id=") + 3, url.indexOf("&"));
                return "http://m.maoyan.com/information/" + id + "?_v_=yes";
            }else {
                String id = url.substring(url.indexOf("id=") + 3);
                return "http://m.maoyan.com/information/" + id + "?_v_=yes";
            }
        }else if(url.contains("ID=")){
            if(url.contains("&")) {
                String id = url.substring(url.indexOf("ID=") + 3, url.indexOf("&"));
                return "http://m.maoyan.com/topic/" + id + "?_v_=yes";
            }else {
                String id = url.substring(url.indexOf("ID=") + 3);
                return "http://m.maoyan.com/topic/" + id + "?_v_=yes";
            }
        }
        return new Exception("Error Url").toString();

    }
}
