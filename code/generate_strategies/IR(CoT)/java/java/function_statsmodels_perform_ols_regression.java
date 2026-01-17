package com.example;

import java.util.*;
import java.util.regex.*;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class OLSRegression {

    
    public String perform_ols_regression(String data_str, String dependent_var, String independent_vars_str) {
        
        String json = data_str.replace("'", "\"");

        
        Map<String, List<Double>> dataMap = new HashMap<>();
        Pattern pattern = Pattern.compile("\"(.*?)\"\\s*:\\s*\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(json);
        while(matcher.find()){
            String key = matcher.group(1);
            String valuesStr = matcher.group(2).trim();
            List<Double> values = new ArrayList<>();
            if(!valuesStr.isEmpty()){
                String[] parts = valuesStr.split(",");
                for(String part: parts){
                    try{
                        values.add(Double.parseDouble(part.trim()));
                    } catch(NumberFormatException e){
                        
                        throw new IllegalArgumentException("Dependent variable contains non-numeric data.");
                    }
                }
            }
            dataMap.put(key, values);
        }

        
        if(dataMap.isEmpty()){
            throw new IllegalArgumentException("Data must be a dictionary.");
        }

        
        if(!dataMap.containsKey(dependent_var)){
            throw new NoSuchElementException("KeyError: " + dependent_var);
        }
        List<Double> yList = dataMap.get(dependent_var);
        
        if(yList.size() < 2){
            throw new IllegalArgumentException("Insufficient data for regression.");
        }
        
        for(Double d : yList){
            if(d == null){
                throw new IllegalArgumentException("Dependent variable contains non-numeric data.");
            }
        }

        
        String[] indepVars = independent_vars_str.split(",");
        List<String> indepVarList = new ArrayList<>();
        for(String s : indepVars){
            indepVarList.add(s.trim());
        }

        int n = yList.size(); 
        int p = indepVarList.size(); 

        
        double[][] X = new double[n][p+1];
        double[] y = new double[n];
        for(int i=0; i<n; i++){
            X[i][0] = 1.0; 
            for(int j=0; j<p; j++){
                String key = indepVarList.get(j);
                if(!dataMap.containsKey(key)){
                    throw new NoSuchElementException("KeyError: " + key);
                }
                List<Double> col = dataMap.get(key);
                X[i][j+1] = col.get(i); 
            }
            y[i] = yList.get(i);
        }

        
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        
        regression.setNoIntercept(false);
        regression.newSampleData(y, X);
        double rSquared = regression.calculateRSquared();
        double[] params = regression.estimateRegressionParameters();

        
        Map<String, Double> coefMap = new LinkedHashMap<>();
        coefMap.put("const", params[0]);
        for(int j=0; j<p; j++){
            coefMap.put(indepVarList.get(j), params[j+1]);
        }

        
        return "R-squared: " + rSquared + ", Coefficients: " + coefMap.toString();
    }
}