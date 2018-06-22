package com.architecture.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;

import java.util.List;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by lz on 2016/7/26.
 */
public class ApplicationUtil {

    /**
     * 低版本API中，不提供此常量
     * Constant for {@link #importance}: This process is running the foreground
     * UI, but the device is asleep so it is not visible to the user.  This means
     * the user is not really aware of the process, because they can not see or
     * interact with it, but it is quite important because it what they expect to
     * return to once unlocking the device.
     */
    public static final int IMPORTANCE_TOP_SLEEPING = 150;

    /**
     * 判断应用是否在后台运行
     * (此接口4.0+、5.0+适用，6.0+不适用)
     *
     * @return
     */
    public static boolean isRunningOnBackground(Context context) {
        boolean flag = false;
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses != null) {
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(context.getPackageName())) {
                    if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND ||
                            appProcess.importance == IMPORTANCE_TOP_SLEEPING) {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    public static boolean isInCallState(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
        return telephonyManager.getCallState() != TelephonyManager.CALL_STATE_IDLE;
    }

    public static void jumpToDownLoadPage(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(it);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断是否从安装界面打开应用
     * <p>
     * 此方法适用于Android 6.0+以上系统。由于Android 6.0+系统
     * 从安装界面是打开的
     * Intent(11-20 10:06:52.724 24202-24202/com.uu.uunavi E/BootActivity:
     * Intent { act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] flg=0x10000000 pkg=com.uu.uunavi cmp=com.uu.uunavi/.ui.base.BootActivity })
     * <p>
     * 与从桌面启动图标打开应用时的
     * Intent(11-20 10:39:23.550 6334-6334/com.uu.uunavi E/BootActivity:
     * Intent { act=android.intent.action.MAIN cat=[android.intent.category.LAUNCHER] flg=0x10000000 cmp=com.uu.uunavi/.ui.base.BootActivity })
     * 注，从桌面启动图标打开应用时，只有首次启动系统才会发送Intent。
     * 不同
     * <p>
     * 导致Android 6.0+从安装界面打开应用时-->应用切换到后台-->点击桌面图标 此过程会重启应用程序。
     * <p>
     * 使用此接口判断，返回 true ：从安装界面打开；false ：从桌面图标打开。
     *
     * @param intent
     * @return
     */
    public static boolean openedFromInstaller(Intent intent) {
        return (intent.getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0;
    }
}
