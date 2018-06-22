package com.architecture.biz.initialize;

import java.util.LinkedList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by lz on 2016/7/27.
 * 依赖任务
 */
public class DependentTask {

    private LinkedList<FutureTask<Boolean>> tasks = new LinkedList<FutureTask<Boolean>>();

    private Executor executor = Executors.newSingleThreadExecutor();

    public void addTask(FutureTask<Boolean> task) {
        tasks.add(task);
    }

    public void execute() {
        for (FutureTask<Boolean> task : tasks) {
            executor.execute(task);
        }
    }
}
