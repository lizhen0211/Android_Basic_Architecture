package com.architecture.base;

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
        application.exitApplication();
    }
}
