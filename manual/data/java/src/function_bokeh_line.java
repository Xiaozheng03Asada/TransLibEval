package com.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Plotter {
    public String create_line_plot(String x_values, String y_values, String title) {
        class Plotter {
            public String createLinePlot(String xValues, String yValues, String plotTitle) {
                if (xValues == null || yValues == null || xValues.isEmpty() || yValues.isEmpty()) {
                    return "Error: x_values and y_values must not be empty.";
                }

                try {
                    List<String> xValuesList = Arrays.stream(xValues.replace("[", "").replace("]", "").split(","))
                            .map(String::trim)
                            .map(s -> s.replace("\"", ""))
                            .collect(Collectors.toList());

                    List<Integer> yValuesList = Arrays.stream(yValues.replace("[", "").replace("]", "").split(","))
                            .map(String::trim)
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());

                    if (xValuesList.size() != yValuesList.size()) {
                        return "Error: x_values and y_values must have the same length.";
                    }

                    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                    for (int i = 0; i < xValuesList.size(); i++) {
                        dataset.addValue(yValuesList.get(i), "Series", xValuesList.get(i));
                    }

                    JFreeChart chart = ChartFactory.createLineChart(
                            plotTitle,
                            "X Axis",
                            "Y Axis",
                            dataset,
                            PlotOrientation.VERTICAL,
                            false,
                            true,
                            false
                    );

                    ChartUtils.saveChartAsJPEG(new File("line_plot.jpg"), chart, 800, 600);
                    return "Plot displayed.";

                } catch (Exception e) {
                    return "Error: Invalid input format.";
                }
            }
        }

        Plotter plotter = new Plotter();
        return plotter.createLinePlot(x_values, y_values, title != null ? title : "Line Plot");
    }
}