package com.example;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;
import java.util.ArrayList;
import java.util.List;

public class PolarsExample {
    public Double compute_sum(Double x, Double y, Double z) {
        
        class PolarsExample {
            public Double computeSum(Double x, Double y, Double z) {
                List<Double> values = new ArrayList<>();
                if (x != null) values.add(x);
                if (y != null) values.add(y);
                if (z != null) values.add(z);

                if (values.isEmpty()) {
                    return 0.0;
                }

                
                DoubleColumn valuesColumn = DoubleColumn.create("values", values);
                Table table = Table.create().addColumns(valuesColumn);
                return table.doubleColumn("values").sum();
            }
        }

        
        return new PolarsExample().computeSum(x, y, z);
    }
}