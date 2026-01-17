package com.example;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

public class StandardizeData {
    public String standardize(Double valueA, Double valueB) {
        if (valueA == null || valueB == null) {
            return "A: None, B: None";
        }

        
        Table table = Table.create("data");
        table.addColumns(
                DoubleColumn.create("A", new Double[]{valueA}),
                DoubleColumn.create("B", new Double[]{valueB})
        );

        
        Mean mean = new Mean();
        StandardDeviation stdDev = new StandardDeviation();

        double meanA = mean.evaluate(new double[]{valueA});
        double stdA = stdDev.evaluate(new double[]{valueA});
        double meanB = mean.evaluate(new double[]{valueB});
        double stdB = stdDev.evaluate(new double[]{valueB});

        
        double standardizedA = (stdA != 0) ? (valueA - meanA) / stdA : 0;
        double standardizedB = (stdB != 0) ? (valueB - meanB) / stdB : 0;

        
        standardizedA = Double.isNaN(standardizedA) ? 0 : standardizedA;
        standardizedB = Double.isNaN(standardizedB) ? 0 : standardizedB;

        return String.format("A: %f, B: %f", standardizedA, standardizedB);
    }
}