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
            
            Table table = Table.read().csv(
                    CsvReadOptions.builder(new StringReader(csv_data))
                            .missingValueIndicator("")
                            .build()
            );

            
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();

            
            for (String colName : table.columnNames()) {
                double missingCount = table.column(colName).countMissing();
                double totalCount = table.rowCount();
                dataset.addValue((totalCount - missingCount) / totalCount, "Present", colName);
            }

            
            JFreeChart chart = ChartFactory.createBarChart(
                    "Missing Data Visualization",
                    "Columns",
                    "Data Completeness",
                    dataset
            );

            
            BufferedImage image = chart.createBufferedImage(500, 300);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            return Base64.getEncoder().encodeToString(baos.toByteArray());

        } catch (Exception e) {
            throw new RuntimeException("Error processing data", e);
        }
    }
}