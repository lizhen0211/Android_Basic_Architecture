package com.architecture.base;

import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by LZ on 2016/5/30.
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    BaseApplication application;

    public CrashHandler(BaseApplication application) {
        this.application = application;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        ex.printStackTrace();
        showExitMessage();
        application.exitApplication();
        application.killProcess();
    }

    private String getStackTrace(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        return writer.toString();
    }


    private void showExitMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Looper.prepare();
                    Toast toast = Toast.makeText(BaseApplication.applicationContext, "�ܱ�Ǹ,��������쳣.�����˳�", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    Looper.loop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
