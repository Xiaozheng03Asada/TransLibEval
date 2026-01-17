package com.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Plotter {
    public String create_bar_chart(String x_values, String y_values, String title) {
        class Plotter {
            public String createBarChart(String x_values, String y_values, String title) {
                try {
                    // 处理x值
                    List<String> x_values_list = Arrays.stream(x_values
                                    .replace("[", "").replace("]", "").split(","))
                            .map(x -> x.trim().replace("\"", ""))
                            .collect(Collectors.toList());

                    // 处理y值
                    List<Integer> y_values_list = Arrays.stream(y_values
                                    .replace("[", "").replace("]", "").split(","))
                            .map(String::trim)
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());

                    if (x_values_list.isEmpty() || y_values_list.isEmpty()) {
                        return "Error: x_values and y_values must not be empty.";
                    }
                    if (x_values_list.size() != y_values_list.size()) {
                        return "Error: x_values and y_values must have the same length.";
                    }

                    // 创建数据集
                    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                    for (int i = 0; i < x_values_list.size(); i++) {
                        dataset.addValue(y_values_list.get(i), "Value", x_values_list.get(i));
                    }

                    // 创建图表
                    JFreeChart chart = ChartFactory.createBarChart(
                            title != null ? title : "Bar Chart",
                            "X Axis",
                            "Y Axis",
                            dataset,
                            PlotOrientation.VERTICAL,
                            false,
                            false,
                            false
                    );

                    // 保存图表
                    ChartUtils.saveChartAsJPEG(new File("bar_chart.jpg"), chart, 800, 600);
                    return "Plot displayed.";

                } catch (NumberFormatException e) {
                    return "Error: Invalid input format.";
                } catch (IOException e) {
                    return "Error: Failed to save chart.";
                }
            }
        }

        Plotter plotter = new Plotter();
        return plotter.createBarChart(x_values, y_values, title);
    }
}