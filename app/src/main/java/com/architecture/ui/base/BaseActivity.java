package com.architecture.ui.base;

import android.app.Activity;
import android.os.Bundle;

import com.architecture.base.BaseApplication;
import com.architecture.biz.dao.DaoSession;

/**
 * Created by lz on 2016/7/18.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public DaoSession getDaoSession() {
        return ((BaseApplication) getApplication()).getDaoSession();
    }

    public void exitApplication() {
        ((BaseApplication) getApplication()).exitApplication();
    }

    public void finishActivity(Class<?>... activityClasses) {
        ((BaseApplication) getApplication()).finishActivity(activityClasses);
    }
}
