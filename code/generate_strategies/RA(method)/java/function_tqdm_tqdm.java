package com.example;

import me.tongfei.progressbar.ProgressBar;

public class ProgressBar {

    public String might_fail_function(String data) {
        StringBuilder result = new StringBuilder();
        try (ProgressBar pb = new ProgressBar("Processing", data.length())) {
            for (char element : data.toCharArray()) {
                result.append(element);
                pb.step();
            }
        }
        return result.toString();
    }
}