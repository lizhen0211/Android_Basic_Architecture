package com.architecture.biz.initialize;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by lz on 2016/7/27.
 */
public class InitializeManager {

    InDependentTask inDependentTask;

    private InitializeCompleteListener _listener;

    public void initialize(InitializeCompleteListener listener) {
        _listener = listener;
        inDependentTask = new InDependentTask();
        inDependentTask.addTask(new FutureTask<Boolean>(new ShowWelcomeCallable()));
        inDependentTask.execute();
        Thread thread = new Thread(new GetResult());
        thread.start();
    }

    class GetResult implements Runnable {
        @Override
        public void run() {
            inDependentTask.get();
            if (_listener != null)
                _listener.onInitializeComplete();
        }
    }
}

class ShowWelcomeCallable implements Callable<Boolean> {

    @Override
    public Boolean call() throws Exception {
        Thread.sleep(2000);
        return true;
    }
}



