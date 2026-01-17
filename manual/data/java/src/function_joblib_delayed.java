package com.example;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DelayedExample {

    public String apply_delayed_function(int x, int y) {
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(1));

        try {
            Callable<Integer> addFunc = () -> x + y;
            ListenableFuture<Integer> future = executorService.submit(addFunc);
            return String.valueOf(future.get());
        } catch (Exception e) {
            return null;
        } finally {
            executorService.shutdown();
        }
    }
}