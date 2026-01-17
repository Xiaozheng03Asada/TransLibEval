package com.example;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

public class PolarsExample {
    public float compute_max(float x, Float y, Float z) {
        
        java.util.List<Double> values = new java.util.ArrayList<>();
        values.add((double) x);
        if (y != null) values.add(y.doubleValue());
        if (z != null) values.add(z.doubleValue());

        if (values.isEmpty()) {
            return 0; 
        }

        
        DoubleColumn column = DoubleColumn.create("values", values);
        Table table = Table.create().addColumns(column);
        return (float) column.max();
    }
}