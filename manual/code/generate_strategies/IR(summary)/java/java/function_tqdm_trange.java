package com.example;

import me.tongfei.progressbar.ProgressBar;

public class ProgressRange {

    public String might_fail_function(int start, int stop, int step, String desc, boolean ascii, Integer miniters, Float maxinterval, Float mininterval) {
        String results = "";
        try (ProgressBar pb = new ProgressBar(desc, (stop - start + step - 1) / step)) { 
            for (int i = start; i < stop; i += step) {
                pb.step();
                String result = String.valueOf(i * 2);
                results += result;
            }
        }
        return results;
    }
}