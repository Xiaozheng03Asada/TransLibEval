package com.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvReadOptions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;
import java.util.Base64;

public class DataMissingVisualizer {
    public String visualize_missing_data(String csv_data) {
        try {
            // 使用TableSaw读取CSV数据
            Table table = Table.read().csv(
                    CsvReadOptions.builder(new StringReader(csv_data))
                            .missingValueIndicator("")
                            .build()
            );

            // 创建数据集
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            // 统计每列的缺失值
            for (String colName : table.columnNames()) {
                double missingCount = table.column(colName).countMissing();
                double totalCount = table.rowCount();
                dataset.addValue((totalCount - missingCount) / totalCount, "Present", colName);
            }

            // 创建条形图
            JFreeChart chart = ChartFactory.createBarChart(
                    "Missing Data Visualization",
                    "Columns",
                    "Data Completeness",
                    dataset
            );

            // 将图表转换为base64字符串
            BufferedImage image = chart.createBufferedImage(500, 300);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Error processing data", e);
        }
    }
}