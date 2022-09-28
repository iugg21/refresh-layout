package com.ctrun.view.cateye.refresh.util;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author ctrun on 2018/6/26.
 */

public class StatusBarUtils {

    /**
     * 状态栏亮色模式，设置状态栏黑色文字、图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     */
    public static void setStatusBarMode(Activity activity, boolean light) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }

        boolean dark = light ? true : false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Window window = activity.getWindow();
            View decorView = window.getDecorView();
            if (decorView != null) {
                setStatusBarDarkIcon(decorView, dark);
            }
        }

        if (setMIUIStatusBarLightMode(activity, dark)) {
            return;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (setMeizuStatusBarLightMode(activity, dark)) {
                return;
            }
        }

    }

    /**
     * 设置状态栏颜色
     *
     */
    private static void setStatusBarDarkIcon(View view, boolean dark) {
        int oldVis = view.getSystemUiVisibility();
        int newVis = oldVis;
        if (dark) {
            newVis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        } else {
            newVis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        if (newVis != oldVis) {
            view.setSystemUiVisibility(newVis);
        }
    }

    /**
     * 需要MIUIV6以上
     * 在新的 MIUI 版本（即基于 Android 6.0 ，开发版 7.7.13 及以后版本）采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
     * 小米开发者文档 https://dev.mi.com/console/doc/detail?pId=1159
     *
     * @param dark 是否把状态栏文字及图标颜色设置为深色
     *
     */
    private static boolean setMIUIStatusBarLightMode(Activity activity, boolean dark) {
        Class<? extends Window> clazz = activity.getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(activity.getWindow(), dark ? darkModeFlag : 0, darkModeFlag);
            return true;
        } catch (Exception e) {

        }

        return false;
    }

    /**
     * 设置状态栏字体图标颜色
     * 魅族开放开发者文档 http://open-wiki.flyme.cn/index.php
     *
     * @param dark  是否深色 true为深色 false 为白色
     */
    private static boolean setMeizuStatusBarLightMode(Activity activity, boolean dark) {
        try {
            WindowManager.LayoutParams winParams = activity.getWindow().getAttributes();
            Field f = winParams.getClass().getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
            f.setAccessible(true);
            int bits = f.getInt(winParams);
            Field f2 = winParams.getClass().getDeclaredField("meizuFlags");
            f2.setAccessible(true);
            int meizuFlags = f2.getInt(winParams);
            int oldFlags = meizuFlags;

            if (dark) {
                meizuFlags |= bits;
            } else {
                meizuFlags &= ~bits;
            }
            if (oldFlags != meizuFlags) {
                f2.setInt(winParams, meizuFlags);
            }

            return true;
        } catch (Exception e) {

        }

        return false;
    }

    private static boolean isMiUi(Resources resources) {
        return resources.getClass().getName().equals("android.content.res.MiuiResources");
    }

    private static boolean isVivo(Resources resources) {
        return resources.getClass().getName().equals("android.content.res.VivoResources");
    }
}