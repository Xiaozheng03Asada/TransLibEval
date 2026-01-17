package com.example;

import org.ojalgo.optimisation.Variable;

public class CVXPYVariableFunction {
    public String process_variables(String x_value, String y_value, String constraint) {
        
        class ProcessResult {
            private final String[] variables;
            private final String message;
            private final boolean isVariables;

            private ProcessResult(String[] variables) {
                this.variables = variables;
                this.message = null;
                this.isVariables = true;
            }

            private ProcessResult(String message) {
                this.variables = null;
                this.message = message;
                this.isVariables = false;
            }

            public String getResult() {
                if (isVariables) {
                    return String.format("%s,%s", variables[0], variables[1]);
                }
                return message;
            }
        }

        class CVXPYVariableFunction {
            public String process_variables(String x_value, String y_value, String constraint) {
                ProcessResult result;

                if (x_value == null && y_value == null && constraint == null) {
                    Variable x = Variable.make("x");
                    Variable y = Variable.make("y");
                    result = new ProcessResult(new String[]{x.toString(), y.toString()});
                    return result.getResult();
                }

                if (x_value != null && y_value != null) {
                    try {
                        double x = Double.parseDouble(x_value);
                        double y = Double.parseDouble(y_value);
                        if (x < 0 || y < 0) {
                            result = new ProcessResult("Error: Variable size cannot be negative");
                            return result.getResult();
                        }
                        result = new ProcessResult(String.format("x: %.1f, y: %.1f", x, y));
                        return result.getResult();
                    } catch (NumberFormatException e) {
                        result = new ProcessResult("Error: Invalid value for x or y");
                        return result.getResult();
                    }
                }

                if (constraint != null) {
                    if (constraint instanceof String) {
                        result = new ProcessResult("Error: Invalid variable constraint");
                        return result.getResult();
                    }
                    result = new ProcessResult("Constraint is valid");
                    return result.getResult();
                }

                result = new ProcessResult("Error: Invalid input");
                return result.getResult();
            }
        }

        CVXPYVariableFunction func = new CVXPYVariableFunction();
        return func.process_variables(x_value, y_value, constraint);
    }
}