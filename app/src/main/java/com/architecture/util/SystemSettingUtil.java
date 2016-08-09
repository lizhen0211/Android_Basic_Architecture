package com.architecture.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

/**
 * Created by lz on 2016/8/9.
 */
public class SystemSettingUtil {

    /**
     * 检测SIM卡是否可用
     *
     * @param content
     * @return true 可用；false 不可用
     */
    public static boolean checkSIMIsAvaliable(Context content) {
        try {
            TelephonyManager mgr = (TelephonyManager) content
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (mgr != null) {
                return TelephonyManager.SIM_STATE_READY == mgr.getSimState();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 检测APN是否正确(只要不是wap就认为正确)
     *
     * @param context : 页面上下文对象
     * @return true：APN正常或者是无效的 false：apn是不正确的
     */
    public static boolean checkAPNType(Context context) {
        try {
            String apnName = "wap";
            ConnectivityManager conManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conManager.getActiveNetworkInfo();
            if (null != netInfo && netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // 获取接入点名称
                String typeName = netInfo.getExtraInfo();
                if ((null == typeName) || ("".equals(typeName))) {
                    return true;
                } else {
                    String lowerTypename = typeName.toLowerCase();
                    if (null != lowerTypename && lowerTypename.contains(apnName)) {
                        return false;
                    } else {
                        return true;
                    }
                }
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 检测飞行模式是否可用
     *
     * @param context :页面上下文对象
     */
    public static boolean checkAirlineMode(Context context) {
        ContentResolver cr = context.getContentResolver();
        if (cr != null) {
            // 获取当前飞行模式状态,返回的是String值0或1。0为关闭飞行,1为开启飞行
            return !"0".equals(Settings.System.getString(cr,
                    Settings.Global.AIRPLANE_MODE_ON));
        } else {
            return false;
        }

    }

    /**
     * 检测GPS是否打开
     *
     * @param context :页面上下文对象
     * @return true:GPS已经打开 false：GPS没有打开
     */
    public static boolean checkGPSState(Context context) {
        try {
            LocationManager locationManager = (LocationManager) context
                    .getSystemService(Context.LOCATION_SERVICE);
            if (null != locationManager) {
                return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 检查移动网络是否已经打开
     *
     * @param context
     * @param methodName
     * @param arg
     * @return
     * @throws Exception
     */

    public static boolean invokeMethod(Context context, String methodName,
                                       Object[] arg) throws Exception {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        Class<?> ownerClass = mConnectivityManager.getClass();
        Class<?>[] argsClass = null;
        if (arg != null) {
            argsClass = new Class[1];
            argsClass[0] = arg.getClass();
        }
        Method method = ownerClass.getMethod(methodName, argsClass);
        Boolean isOpen = (Boolean) method.invoke(mConnectivityManager, arg);
        return isOpen;
    }

    /**
     * 获取移动网络连接状态
     *
     * @param context :页面上下文对象
     * @return 移动网络连接状态 true：已连接 false:没有连接
     */
    public static boolean getMobileConnectedState(Context context) {
        Object[] arg = null;
        try {
            boolean state = invokeMethod(context, "getMobileDataEnabled",
                    arg);
            return state;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * wifi或者移动网络有一个连接正常就代表连接正常
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnect(Context context) {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                // 获取网络连接管理的对象
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                        // 判断当前网络是否已经连接
                        if (info.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 当wifi连接，GPRS没有连接时显示打开移动网络，否则不显示
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnectedMobileNotConnected(Context context) {
        boolean isShow = false;
        if (isWifiConnect(context) && (!getMobileConnectedState(context))) {
            isShow = true;
        }
        return isShow;
    }

    /**
     * 当wifi和GPRS都没有连接时，显示连接移动网络
     *
     * @param context
     * @return
     */
    public static boolean isWifiAndMobileNotConnected(Context context) {
        boolean isShow = false;
        if ((!isWifiConnect(context) && (!getMobileConnectedState(context)))) {
            isShow = true;
        }
        return isShow;
    }

    /**
     * 检测媒体音量是否太小
     *
     * @param context
     * @return 当前的声音小于30% true
     */
    public static boolean isAudioVolumeSoSmall(Context context) {
        try {
            AudioManager mAudioManager = (AudioManager) context
                    .getSystemService(Context.AUDIO_SERVICE);
            // 音乐音量
            double max = mAudioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            double current = mAudioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC);
            double proportion = current / max;
            boolean isSmall = true;
            final double smallProportion = 0.3d;
            if (smallProportion > proportion) {
                isSmall = true;
            } else {
                isSmall = false;
            }
            return isSmall;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    //*******************************   打开系统设置菜单 start *******************************//

    /**
     * 打开系统设置
     *
     * @param context :页面上下文对象
     */
    public static void openSystemSetting(Context context) {
        Intent intent = new Intent(Settings.ACTION_SETTINGS);
        context.startActivity(intent);
    }

    /**
     * 打开飞行模式设置
     *
     * @param context :页面上下文对象
     */
    public static void openAirplaneModeModeSetting(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            //个别手机无飞行模式设置，则打开系统设置
            openSystemSetting(context);
        }
    }

    /**
     * 打开GPS设置
     *
     * @param context 页面上下文对象
     */
    public static void openGPSSetting(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            openSystemSetting(context);
        }
    }

    /**
     * 打开apn设置
     *
     * @param context :页面上下文对象
     */
    public static void settingAPN(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_APN_SETTINGS);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            openSystemSetting(context);
        }
    }

    /**
     * 打开媒体音量设置
     *
     * @param context
     */
    public static void settingSound(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_SOUND_SETTINGS);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            openSystemSetting(context);
        }
    }

    //*******************************   打开系统设置菜单 end *******************************//

    //*******************************   直接打开系统设置 start *******************************//

    /**
     * 打开移动网络
     *
     * @param context
     */
    public static void openMobileNet(Context context) {
        try {
            // 自动打开移动网络
            invokeBooleanArgMethod(context,
                    "setMobileDataEnabled", true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开移动网络
     *
     * @param context
     * @param methodName
     * @param value
     * @return
     * @throws Exception
     */
    private static Object invokeBooleanArgMethod(Context context,
                                                 String methodName, boolean value) throws Exception {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        Class<?> ownerClass = mConnectivityManager.getClass();
        Class<?>[] argsClass = new Class[1];
        argsClass[0] = boolean.class;
        Method method = ownerClass.getMethod(methodName, argsClass);
        return method.invoke(mConnectivityManager, value);
    }
    //*******************************   直接打开系统设置 end *******************************//
}
