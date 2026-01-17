package com.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.JFrame;
import java.util.Random;

public class LearningCurvePlotter {
    public String plot_learning_curve(int n_samples, int n_features, int n_classes) {
        class LearningCurvePlotter {
            public String plot() {
                if (n_samples <= 0) {
                    return "Invalid number of samples";
                }

                try {
                    Random rand = new Random(42);
                    XYSeries trainSeries = new XYSeries("Training Score");
                    XYSeries testSeries = new XYSeries("Test Score");

                    // 生成模拟的学习曲线数据
                    double baseTrainScore = 0.5; // 基础训练分数
                    double baseTestScore = 0.4;  // 基础测试分数

                    for (int i = 10; i <= n_samples; i += Math.max(1, n_samples/10)) {
                        // 训练分数随样本量增加而提高，但增长速度逐渐降低
                        double trainNoise = rand.nextDouble() * 0.1 - 0.05;
                        double trainScore = Math.min(0.99, baseTrainScore +
                                0.3 * (1 - Math.exp(-i / (double)n_samples)));
                        trainSeries.add(i, trainScore + trainNoise);

                        // 测试分数也随样本量增加而提高，但增长幅度较小
                        double testNoise = rand.nextDouble() * 0.1 - 0.05;
                        double testScore = Math.min(0.95, baseTestScore +
                                0.25 * (1 - Math.exp(-i / (double)n_samples)));
                        testSeries.add(i, testScore + testNoise);
                    }

                    // 创建数据集合
                    XYSeriesCollection dataset = new XYSeriesCollection();
                    dataset.addSeries(trainSeries);
                    dataset.addSeries(testSeries);

                    // 创建图表
                    JFreeChart chart = ChartFactory.createXYLineChart(
                            "Learning Curves",
                            "Training examples",
                            "Score",
                            dataset,
                            PlotOrientation.VERTICAL,
                            true,
                            true,
                            false
                    );

                    // 显示图表
                    JFrame frame = new JFrame("Learning Curves");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    ChartPanel chartPanel = new ChartPanel(chart);
                    frame.add(chartPanel);
                    frame.pack();
                    frame.setVisible(true);

                    return "Learning curve plotted successfully";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Error plotting learning curve";
                }
            }
        }
        return new LearningCurvePlotter().plot();
    }
}