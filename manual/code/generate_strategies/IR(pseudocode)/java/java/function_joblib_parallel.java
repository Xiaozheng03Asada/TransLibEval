package com.example;

import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ParallelProcessor {

    public String run_parallel(Integer data_1, Float data_2, Double data_3, Integer data_4, Integer data_5, Integer data_6, Integer data_7, Integer data_8, Integer data_9, Integer data_10, int n_jobs) {

        BasicThreadFactory factory = new BasicThreadFactory.Builder()
                .namingPattern("example-pool-%d")
                .daemon(true)
                .priority(Thread.MAX_PRIORITY)
                .build();

        ExecutorService executorService = Executors.newFixedThreadPool(n_jobs > 0 ? n_jobs : 1, factory);

        List<Number> dataItems = Arrays.asList(data_1, data_2, data_3, data_4, data_5, data_6, data_7, data_8, data_9, data_10);
        List<Number> validDataItems = dataItems.stream().filter(x -> x != null).collect(Collectors.toList());

        List<CompletableFuture<Integer>> futures = validDataItems.stream()
                .map(x -> CompletableFuture.supplyAsync(() -> {
                    if (x instanceof Integer) {
                        return ((Integer) x) * ((Integer) x);
                    } else if (x instanceof Float) {
                        return (int) Math.pow((Float) x, 2);
                    } else {
                        return (int) Math.pow((Double) x, 2);
                    }
                }, executorService))
                .collect(Collectors.toList());

        List<Integer> results = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        executorService.shutdown();

        return results.stream().map(String::valueOf).collect(Collectors.joining(", "));
    }
}