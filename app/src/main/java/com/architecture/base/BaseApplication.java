package com.architecture.base;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.architecture.biz.dao.DaoMaster;
import com.architecture.biz.dao.DaoSession;

import org.greenrobot.greendao.database.Database;

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

    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();
        initDataBase();
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

    public void initConfig() {
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

}
