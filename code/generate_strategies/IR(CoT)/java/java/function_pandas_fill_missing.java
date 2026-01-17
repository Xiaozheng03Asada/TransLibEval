package com.example;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

public class FillMissingValues {
    public String fill_missing_values(Double value_A, Double value_B) {
        Table table = Table.create("data");

        
        DoubleColumn colA = DoubleColumn.create("A", 1);
        DoubleColumn colB = DoubleColumn.create("B", 1);
        colA.set(0, value_A);
        colB.set(0, value_B);
        table.addColumns(colA, colB);

        if (value_A == null && value_B == null) {
            return "A: nan, B: nan";
        } else {
            
            double meanA = value_A != null ? value_A : Double.NaN;
            double meanB = value_B != null ? value_B : Double.NaN;

            
            double finalA = Double.isNaN(meanA) ? Double.NaN : meanA;
            double finalB = Double.isNaN(meanB) ? Double.NaN : meanB;

            String resultA = Double.isNaN(finalA) ? "nan" : String.valueOf(finalA);
            String resultB = Double.isNaN(finalB) ? "nan" : String.valueOf(finalB);

            return String.format("A: %s, B: %s", resultA, resultB);
        }
    }
}