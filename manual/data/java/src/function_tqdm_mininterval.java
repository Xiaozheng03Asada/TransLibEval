package com.example;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ProgressBar {
    public int might_fail_function(float mininterval_value) {
        class ProgressBarWrapper {
            private int processWithProgress(float minInterval) {
                List<Integer> results = new ArrayList<>();

                // 确保最小更新间隔不小于1毫秒
                int updateInterval = Math.max(1, (int)(minInterval * 1000));

                try (ProgressBar pb = new ProgressBarBuilder()
                        .setTaskName("Processing")
                        .setInitialMax(10)
                        .setStyle(ProgressBarStyle.ASCII)
                        .setUpdateIntervalMillis(updateInterval)
                        .build()) {

                    IntStream.range(0, 10).forEach(i -> {
                        try {
                            Thread.sleep(100); // 100ms延迟
                            results.add(i);
                            pb.step();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            throw new RuntimeException(e);
                        }
                    });
                }

                return results.size();
            }
        }

        if (Float.isNaN(mininterval_value)) {
            throw new IllegalArgumentException("mininterval must be a number");
        }

        return new ProgressBarWrapper().processWithProgress(mininterval_value);
    }
}