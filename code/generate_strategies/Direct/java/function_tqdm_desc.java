package com.example;

import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;

public class ProgressBar {
    public String process_with_progress_bar(String data, String desc_text) {
        class TypeError extends RuntimeException {
            public TypeError(String message) {
                super(message);
            }
        }

        class ProgressBarWrapper {
            String process_with_progress_bar(String data, String desc_text) {
                if (desc_text != null && !(desc_text instanceof String)) {
                    throw new TypeError("desc_text must be a string.");
                }

                StringBuilder results = new StringBuilder();
                try (ProgressBar pb = new ProgressBarBuilder()
                        .setTaskName(desc_text != null ? desc_text : "")
                        .setInitialMax(data.length())
                        .build()) {

                    for (char element : data.toCharArray()) {
                        String processed_element = String.valueOf(
                                Integer.parseInt(String.valueOf(element)) * 2
                        );
                        results.append(processed_element);
                        pb.step();
                    }
                }
                return results.toString();
            }
        }

        return new ProgressBarWrapper().process_with_progress_bar(data, desc_text);
    }
}