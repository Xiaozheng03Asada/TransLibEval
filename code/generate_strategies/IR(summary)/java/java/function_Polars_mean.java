package com.example;

import org.apache.commons.math3.stat.descriptive.moment.Mean;
import java.util.ArrayList;
import java.util.List;

public class PolarsExample {
    public Double compute_mean(Float x, Float y, Float z) {
        
        List<Double> values = new ArrayList<>();

        
        if (x != null) values.add(x.doubleValue());
        if (y != null) values.add(y.doubleValue());
        if (z != null) values.add(z.doubleValue());

        
        if (values.isEmpty()) {
            return null;
        }

        
        Mean mean = new Mean();
        return mean.evaluate(values.stream().mapToDouble(d -> d).toArray());
    }
}