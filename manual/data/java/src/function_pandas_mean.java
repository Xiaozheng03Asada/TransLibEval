package com.example;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

public class CalculateMean {
    public String calculate_mean(Double value_A, Double value_B) {
        Double mean_A;
        Double mean_B;

        if (value_A == null || value_B == null) {
            mean_A = null;
            mean_B = null;
        } else {
            Table table = Table.create()
                    .addColumns(
                            DoubleColumn.create("A", new Double[]{value_A}),
                            DoubleColumn.create("B", new Double[]{value_B})
                    );

            mean_A = table.doubleColumn("A").mean();
            mean_B = table.doubleColumn("B").mean();
        }

        return String.format("A: %s, B: %s",
                mean_A == null ? "None" : mean_A,
                mean_B == null ? "None" : mean_B);
    }
}