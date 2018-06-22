package com.architecture.biz.initialize;

import java.util.LinkedList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by lz on 2016/7/27.
 * 非依赖任务
 */
public class InDependentTask {

    private LinkedList<FutureTask<Boolean>> tasks = new LinkedList<FutureTask<Boolean>>();

    private Executor executor = Executors.newCachedThreadPool();

    public synchronized void addTask(FutureTask<Boolean> task) {
        tasks.add(task);
    }

    public synchronized void execute() {
        for (FutureTask<Boolean> task : tasks) {
            executor.execute(task);
        }
    }

    public synchronized void get() {
        try {
            for (FutureTask<Boolean> task : tasks) {
                task.get();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
