package com.example;

import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Variable;
import org.ojalgo.optimisation.Optimisation;
import org.ojalgo.optimisation.Expression;
import java.util.Arrays;

public class CVXPYSumOfSquaresFunction {
    public String solve_sum_of_squares(String vector_values_str) {
        // Split input string and convert to double array
        double[] vector_values = Arrays.stream(vector_values_str.split(","))
                .mapToDouble(Double::parseDouble)
                .toArray();

        // Create optimization model
        ExpressionsBasedModel model = new ExpressionsBasedModel();

        // Create variables and objective function
        Variable[] variables = new Variable[vector_values.length];
        Expression objective = model.addExpression("objective");

        // Initialize variables and build objective function
        for (int i = 0; i < vector_values.length; i++) {
            variables[i] = Variable.make("x" + i)
                    .lower(vector_values[i])
                    .upper(vector_values[i])
                    .weight(vector_values[i] * vector_values[i]);
            model.addVariable(variables[i]);
            objective.set(variables[i], 1);
        }

        // Solve the model (though in this case, since we've constrained the variables
        // to exact values, we can directly calculate the result)
        double result = Arrays.stream(vector_values)
                .map(x -> x * x)
                .sum();

        return String.valueOf(result);
    }
}