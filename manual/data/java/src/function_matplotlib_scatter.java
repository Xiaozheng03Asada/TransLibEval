package com.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ScatterPlotGenerator {
    public String create_scatter_plot(String x_values, String y_values, String title) {
        class ScatterPlotGenerator {
            public String generate(String x_values, String y_values, String title) {
                if (x_values == null || y_values == null || x_values.isEmpty() || y_values.isEmpty()) {
                    return "Error: x_values and y_values cannot be empty.";
                }

                List<String> xValuesList = Arrays.asList(x_values.split(","));
                List<String> yValuesList = Arrays.asList(y_values.split(","));

                List<Double> xValuesDouble;
                List<Double> yValuesDouble;

                try {
                    xValuesDouble = xValuesList.stream()
                            .map(String::trim)
                            .map(Double::parseDouble)
                            .collect(Collectors.toList());
                    yValuesDouble = yValuesList.stream()
                            .map(String::trim)
                            .map(Double::parseDouble)
                            .collect(Collectors.toList());
                } catch (NumberFormatException e) {
                    return "Error: All x and y values must be numbers.";
                }

                if (xValuesDouble.size() != yValuesDouble.size()) {
                    return "Error: x_values and y_values must have the same length.";
                }

                XYSeries series = new XYSeries("Data");
                for (int i = 0; i < xValuesDouble.size(); i++) {
                    series.add(xValuesDouble.get(i), yValuesDouble.get(i));
                }

                XYSeriesCollection dataset = new XYSeriesCollection();
                dataset.addSeries(series);

                JFreeChart chart = ChartFactory.createScatterPlot(
                        title.isEmpty() ? "Scatter Plot" : title,
                        "X Values",
                        "Y Values",
                        dataset
                );

                try {
                    ChartUtils.saveChartAsPNG(new File("scatter_plot.png"), chart, 800, 600);
                    return "Plot saved as 'scatter_plot.png'.";
                } catch (IOException e) {
                    return "Error: Failed to save the plot.";
                }
            }
        }

        return new ScatterPlotGenerator().generate(x_values, y_values, title);
    }
}