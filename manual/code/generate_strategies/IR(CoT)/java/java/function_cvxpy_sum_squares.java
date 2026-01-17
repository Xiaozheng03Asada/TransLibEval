package com.example;

import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Variable;
import org.ojalgo.optimisation.Optimisation;
import org.ojalgo.optimisation.Expression;
import java.util.Arrays;

public class CVXPYSumOfSquaresFunction {
    public String solve_sum_of_squares(String vector_values_str) {
        
        double[] vector_values = Arrays.stream(vector_values_str.split(","))
                .mapToDouble(Double::parseDouble)
                .toArray();

        
        ExpressionsBasedModel model = new ExpressionsBasedModel();

        
        Variable[] variables = new Variable[vector_values.length];
        Expression objective = model.addExpression("objective");

        
        for (int i = 0; i < vector_values.length; i++) {
            variables[i] = Variable.make("x" + i)
                    .lower(vector_values[i])
                    .upper(vector_values[i])
                    .weight(vector_values[i] * vector_values[i]);
            model.addVariable(variables[i]);
            objective.set(variables[i], 1);
        }

        
        
        double result = Arrays.stream(vector_values)
                .map(x -> x * x)
                .sum();

        return String.valueOf(result);
    }
}