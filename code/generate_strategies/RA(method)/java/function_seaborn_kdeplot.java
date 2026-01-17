package com.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import java.awt.Color;
import java.util.Arrays;
import java.util.stream.Stream;

public class SeabornKDEPlot {
    public String generate_kdeplot(String data, boolean shade, String color) {
        // 在唯一方法内部定义所需的所有类和方法
        class DataValidator {
            void validateInput(String data) {
                if (data == null || !data.getClass().equals(String.class)) {
                    throw new IllegalArgumentException("Input data must be a string representing a list of numbers.");
                }

                if (data.trim().isEmpty()) {
                    throw new IllegalArgumentException("Input data cannot be empty.");
                }
            }
        }

        class DataProcessor {
            double[] processData(String data) {
                try {
                    return Stream.of(data.split(","))
                            .map(String::trim)
                            .mapToDouble(Double::parseDouble)
                            .toArray();
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Input data must be a string with numeric values separated by commas.");
                }
            }
        }

        class ChartCreator {
            void createKDEPlot(double[] data, boolean shade, String colorStr) {
                HistogramDataset dataset = new HistogramDataset();
                dataset.addSeries("KDE", data, 50);

                JFreeChart chart = ChartFactory.createXYLineChart(
                        "KDE Plot",
                        "Value",
                        "Density",
                        dataset
                );

                XYPlot plot = chart.getXYPlot();
                XYSplineRenderer renderer = new XYSplineRenderer();

                Color javaColor;
                switch(colorStr.toLowerCase()) {
                    case "blue": javaColor = Color.BLUE; break;
                    case "red": javaColor = Color.RED; break;
                    case "green": javaColor = Color.GREEN; break;
                    default: javaColor = Color.BLUE;
                }

                renderer.setSeriesPaint(0, javaColor);
                plot.setRenderer(renderer);
            }
        }

        // 执行主要逻辑
        DataValidator validator = new DataValidator();
        validator.validateInput(data);

        DataProcessor processor = new DataProcessor();
        double[] processedData = processor.processData(data);

        ChartCreator creator = new ChartCreator();
        creator.createKDEPlot(processedData, shade, color);

        return String.format("{'data': '%s', 'shade': %b, 'color': '%s'}",
                data, shade, color);
    }
}