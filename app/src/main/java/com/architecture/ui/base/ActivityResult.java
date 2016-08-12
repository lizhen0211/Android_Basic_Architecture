package com.architecture.ui.base;

import android.content.Intent;

/**
 * Created by lz on 2016/8/12.
 */
public class ActivityResult {

    private int resultCode;

    private Intent intent;

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}
