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

public class BarPlotGenerator {

    public String create_bar_plot(String categoriesStr, String valuesStr) {
        try {
            if (categoriesStr == null || categoriesStr.isEmpty() || valuesStr == null || valuesStr.isEmpty()) {
                return "Error: Categories and values cannot be empty.";
            }

            List<String> categories = Arrays.asList(categoriesStr.split(","));
            List<String> values = Arrays.asList(valuesStr.split(","));

            if (categories.size() != values.size()) {
                return "Error: Categories and values must have the same length.";
            }

            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            for (int i = 0; i < categories.size(); i++) {
                try {
                    double value = Double.parseDouble(values.get(i).trim());
                    dataset.addValue(value, "Values", categories.get(i).trim());
                } catch (NumberFormatException e) {
                    return "Error: All values must be numbers.";
                }
            }

            JFreeChart barChart = ChartFactory.createBarChart(
                    "Bar Plot",
                    "Categories",
                    "Values",
                    dataset,
                    PlotOrientation.VERTICAL,
                    false, true, false);

            File barChartFile = new File("bar_plot.png");
            ChartUtils.saveChartAsPNG(barChartFile, barChart, 800, 600);

            return "Plot saved as 'bar_plot.png'.";

        } catch (IOException e) {
            return "Error: An IO exception occurred.";
        }
    }
}