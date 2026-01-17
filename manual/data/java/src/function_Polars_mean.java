package com.example;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import java.util.ArrayList;
import java.util.List;

public class PolarsExample {
    public Double compute_mean(Float x, Float y, Float z) {
        // 创建一个列表存储非null的值
        List<Double> values = new ArrayList<>();

        // 检查并添加非null值
        if (x != null) values.add(x.doubleValue());
        if (y != null) values.add(y.doubleValue());
        if (z != null) values.add(z.doubleValue());

        // 如果列表为空返回null
        if (values.isEmpty()) {
            return null;
        }

        // 使用Apache Commons Math计算平均值
        Mean mean = new Mean();
        return mean.evaluate(values.stream().mapToDouble(d -> d).toArray());
    }
}