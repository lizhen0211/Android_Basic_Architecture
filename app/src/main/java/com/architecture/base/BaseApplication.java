package com.architecture.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.architecture.biz.dao.DaoMaster;
import com.architecture.biz.dao.DaoSession;
import com.architecture.ui.base.ActivityResult;
import com.architecture.ui.base.BaseActivity;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lz on 2016/7/18.
 */
public class BaseApplication extends Application {
    /**
     * A flag to show how easily you can switch from standard SQLite to the encrypted SQLCipher.
     */
    public static final boolean ENCRYPTED = true;

    private DaoSession daoSession;

    public static String packageName;

    public static String versionName;

    public static int versionCode;

    public static String channelName;

    public static Context applicationContext;

    private ArrayList<Activity> activities;

    private CrashHandler crashHandler;

    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        initCrashHandler();
        initOtherSDK();
        initConfig();
        initDataBase();
        initActivityBackRecordStack();
    }

    /**
     * 初始化activity回退栈
     */
    private void initActivityBackRecordStack() {
        activities = new ArrayList<Activity>();
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    /**
     * 初始化异常捕获
     */
    private void initCrashHandler() {
        crashHandler = new CrashHandler(this);
        Thread.setDefaultUncaughtExceptionHandler(crashHandler);
    }

    /**
     * 初始化数据库
     */
    private void initDataBase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    /**
     * 初始化配置
     */
    private void initConfig() {
        applicationContext = getApplicationContext();
        packageName = getPackageName();

        PackageManager packageManager = getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionName = packageInfo.versionName;
        versionCode = packageInfo.versionCode;

        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = packageManager.getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        channelName = applicationInfo.metaData.getString("CHANNEL_KEY");
    }

    private void initOtherSDK() {
        mRefWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
    }

    private ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            activities.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            activities.remove(activity);
        }
    };

    public void exitApplication() {
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            activity.finish();
        }
        activities.clear();
        killProcess();
    }

    public void backToMainActivity() {
        for (int i = activities.size() - 1; i >= 0; i--) {
            Activity activity = activities.get(i);
            if (activity instanceof BaseActivity) {
                if (((BaseActivity) activity).isFinishOnBackToMain()) {
                    activity.finish();
                }
            } else {
                //throw new UnsupportedOperationException("only instance extends BaseActivity can call this method");
            }
        }
    }

    public void finishActivity(Class<?>... activityClasses) {
        for (int i = activities.size() - 1; i >= 0; i--) {
            for (int j = 0; j < activityClasses.length; j++) {
                Activity activity = activities.get(i);
                if (activity.getComponentName().getClassName().equals(activityClasses[j].getName())) {
                    activity.finish();
                }
            }
        }
    }

    public void finishActivityForResult(List<ActivityResult> results, Class<?>... activityClasses) {
        for (int i = activities.size() - 1; i >= 0; i--) {
            for (int j = 0; j < activityClasses.length; j++) {
                Activity activity = activities.get(i);
                if (activity.getComponentName().getClassName().equals(activityClasses[j].getName())) {
                    if (results != null) {
                        if (j < results.size()) {
                            ActivityResult activityResult = results.get(j);
                            if (activityResult != null) {
                                activity.setResult(activityResult.getResultCode(), activityResult.getIntent());
                            }
                        }
                    }
                    activity.finish();
                }
            }
        }
    }

    public void killProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
