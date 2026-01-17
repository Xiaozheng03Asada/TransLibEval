package com.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.io.File;
import java.io.IOException;

public class Plotter {
    public String create_circle_plot(String x_values, String y_values, int radius, String title) {
        class Plotter {
            public String createPlot(String x_values, String y_values, int radius, String title) {
                if (x_values == null || y_values == null || x_values.isEmpty() || y_values.isEmpty()) {
                    return "Error: x_values and y_values must not be empty.";
                }

                try {
                    String[] x_values_list = x_values.replace("[", "").replace("]", "").split(",");
                    String[] y_values_list = y_values.replace("[", "").replace("]", "").split(",");

                    if (x_values_list.length != y_values_list.length) {
                        return "Error: x_values and y_values must have the same length.";
                    }

                    XYSeries series = new XYSeries("Data");
                    for (int i = 0; i < x_values_list.length; i++) {
                        String x = x_values_list[i].trim().replace("\"", "");
                        int y = Integer.parseInt(y_values_list[i].trim());
                        series.add(i, y); // Using index as x since we have string x values
                    }

                    XYSeriesCollection dataset = new XYSeriesCollection();
                    dataset.addSeries(series);

                    JFreeChart chart = ChartFactory.createScatterPlot(
                            title,
                            "X Axis",
                            "Y Axis",
                            dataset
                    );

                    try {
                        ChartUtils.saveChartAsJPEG(new File("circle_plot.jpg"), chart, 800, 600);
                        return "Plot displayed.";
                    } catch (IOException e) {
                        return "Error: Could not save plot.";
                    }
                } catch (NumberFormatException e) {
                    return "Error: Invalid input format.";
                }
            }
        }

        return new Plotter().createPlot(x_values, y_values, radius != 0 ? radius : 10,
                title != null && !title.isEmpty() ? title : "Circle Plot");
    }
}