package com.architecture.ui.base;

import android.app.Activity;
import android.os.Bundle;

import com.architecture.base.BaseApplication;
import com.architecture.biz.dao.DaoSession;

/**
 * Created by lz on 2016/7/18.
 */
public class BaseActivity extends Activity {

    private BaseApplication application = (BaseApplication) getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public DaoSession getDaoSession() {
        return application.getDaoSession();
    }

    public void exitApplication() {
        application.exitApplication();
    }

    public void finishActivity(Class<?>... activityClasses) {
        application.finishActivity(activityClasses);
    }
}
