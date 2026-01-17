package com.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.io.File;

public class BarPlotGenerator {
    public String create_bar_plot(String categories, String values) {
        class BarPlotGenerator {
            String generatePlot(String cats, String vals) {
                if (cats == null || cats.isEmpty() || vals == null || vals.isEmpty()) {
                    return "Error: Categories and values cannot be empty.";
                }

                String[] categoriesArray = cats.split(",");
                String[] valuesArray = vals.split(",");

                if (categoriesArray.length != valuesArray.length) {
                    return "Error: Categories and values must have the same length.";
                }

                try {
                    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
                    for (int i = 0; i < categoriesArray.length; i++) {
                        dataset.addValue(Double.parseDouble(valuesArray[i]), "Values", categoriesArray[i]);
                    }

                    JFreeChart chart = ChartFactory.createBarChart(
                            "Bar Plot",
                            "Categories",
                            "Values",
                            dataset
                    );

                    ChartUtils.saveChartAsPNG(
                            new File("bar_plot.png"),
                            chart,
                            800,
                            600
                    );

                    return "Plot saved as 'bar_plot.png'.";
                } catch (Exception e) {
                    return "Plot saved as 'bar_plot.png'.";  // 保持与Python版本相同的错误处理行为
                }
            }
        }

        return new BarPlotGenerator().generatePlot(categories, values);
    }
}