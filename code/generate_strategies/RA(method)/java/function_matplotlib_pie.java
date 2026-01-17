package com.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class PieChartGenerator {
    public String create_pie_chart(String labels, String sizes, String title) {
        // 定义内部类
        class PieChartGenerator {
            public String createPieChart(String labels, String sizes, String title) {
                if (labels == null || sizes == null || labels.trim().isEmpty() || sizes.trim().isEmpty()) {
                    return "Error: labels and sizes cannot be empty.";
                }

                String[] labelArray = labels.split(",");
                String[] sizeArray = sizes.split(",");

                if (labelArray.length != sizeArray.length) {
                    return "Error: labels and sizes must have the same length.";
                }

                double[] sizeValues;
                try {
                    sizeValues = Arrays.stream(sizeArray)
                            .map(String::trim)
                            .mapToDouble(Double::parseDouble)
                            .toArray();

                    if (Arrays.stream(sizeValues).anyMatch(size -> size < 0)) {
                        return "Error: All sizes must be non-negative numbers.";
                    }
                } catch (NumberFormatException e) {
                    return "Error: All sizes must be non-negative numbers.";
                }

                try {
                    DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
                    for (int i = 0; i < labelArray.length; i++) {
                        dataset.setValue(labelArray[i], sizeValues[i]);
                    }

                    JFreeChart chart = ChartFactory.createPieChart(
                            title != null ? title : "Pie Chart",
                            dataset,
                            true,
                            true,
                            false
                    );

                    ChartUtils.saveChartAsPNG(new File("pie_chart.png"), chart, 800, 600);
                    return "Pie chart saved as 'pie_chart.png'.";
                } catch (IOException e) {
                    return "Error: Failed to save the pie chart.";
                }
            }
        }

        PieChartGenerator generator = new PieChartGenerator();
        return generator.createPieChart(labels, sizes, title != null ? title : "Pie Chart");
    }
}