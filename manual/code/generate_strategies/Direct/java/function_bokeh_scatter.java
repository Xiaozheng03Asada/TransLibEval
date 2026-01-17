package com.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Plotter {
    public String create_scatter_plot(String x_values, String y_values, String title) {
        class Plotter {
            public String createPlot(String xVals, String yVals, String plotTitle) {
                if (xVals == null || yVals == null || xVals.isEmpty() || yVals.isEmpty()) {
                    return "Error: x_values and y_values must not be empty.";
                }

                try {
                    // 将输入的字符串转化为列表
                    List<String> xValuesList = Arrays.stream(xVals.replace("[", "").replace("]", "").split(","))
                            .map(String::trim)
                            .map(s -> s.replace("\"", ""))
                            .collect(Collectors.toList());

                    List<Integer> yValuesList = Arrays.stream(yVals.replace("[", "").replace("]", "").split(","))
                            .map(String::trim)
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());

                    if (xValuesList.size() != yValuesList.size()) {
                        return "Error: x_values and y_values must have the same length.";
                    }

                    // 创建数据集
                    XYSeries series = new XYSeries("Data");
                    for (int i = 0; i < xValuesList.size(); i++) {
                        series.add(i, yValuesList.get(i));
                    }
                    XYDataset dataset = new XYSeriesCollection(series);

                    // 创建图表
                    JFreeChart chart = ChartFactory.createScatterPlot(
                            plotTitle != null ? plotTitle : "Scatter Plot",
                            "X Axis",
                            "Y Axis",
                            dataset
                    );

                    // 保存图表为HTML (这里用PNG代替，因为JFreeChart不直接支持HTML输出)
                    ChartUtils.saveChartAsPNG(new File("scatter_plot.png"), chart, 800, 600);
                    return "Plot displayed.";
                } catch (NumberFormatException e) {
                    return "Error: Invalid input format.";
                } catch (IOException e) {
                    return "Error: Failed to save plot.";
                }
            }
        }

        return new Plotter().createPlot(x_values, y_values, title);
    }
}