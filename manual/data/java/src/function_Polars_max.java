package com.example;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

public class PolarsExample {
    public float compute_max(float x, Float y, Float z) {
        // 创建一个存储非null值的列表
        java.util.List<Double> values = new java.util.ArrayList<>();
        values.add((double) x);
        if (y != null) values.add(y.doubleValue());
        if (z != null) values.add(z.doubleValue());

        if (values.isEmpty()) {
            return 0; // Java中基本类型float不能为null，所以返回0
        }

        // 使用tablesaw（Java中类似于polars的数据处理库）
        DoubleColumn column = DoubleColumn.create("values", values);
        Table table = Table.create().addColumns(column);
        return (float) column.max();
    }
}