package com.example;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class MemoryExample {
    public int compute_square(int x) {
        Cache<Integer, Integer> cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .build();

        try {
            return cache.get(x, new Callable<Integer>() {
                @Override
                public Integer call() {
                    return x * x;
                }
            });
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}