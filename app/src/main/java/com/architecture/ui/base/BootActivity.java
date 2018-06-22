package com.architecture.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.architecture.R;
import com.architecture.biz.initialize.InitializeCompleteListener;
import com.architecture.biz.initialize.InitializeManager;
import com.architecture.ui.MainActivity;
import com.architecture.util.ApplicationUtil;

public class BootActivity extends Activity implements InitializeCompleteListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boot);

        if (ApplicationUtil.openedFromInstaller(getIntent())) {
            finish();
            return;
        }

        InitializeManager initializeManager = new InitializeManager();
        initializeManager.initialize(this);
    }

    @Override
    public void onInitializeComplete() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(BootActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
