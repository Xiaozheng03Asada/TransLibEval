package com.example;

import java.util.*;
import java.util.regex.*;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class OLSRegression {

    // 所有逻辑均在此唯一方法内实现
    public String perform_ols_regression(String data_str, String dependent_var, String independent_vars_str) {
        // 将单引号替换为双引号，便于使用正则解析
        String json = data_str.replace("'", "\"");

        // 使用正则解析字典，匹配 "key": [value1, value2, ...]
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
                        // 无论是因 dependent_var 还是独立变量，只返回相同错误信息以符合测试要求
                        throw new IllegalArgumentException("Dependent variable contains non-numeric data.");
                    }
                }
            }
            dataMap.put(key, values);
        }

        // 如果解析结果为空，则输入不是字典
        if(dataMap.isEmpty()){
            throw new IllegalArgumentException("Data must be a dictionary.");
        }

        // 检查是否包含依赖变量
        if(!dataMap.containsKey(dependent_var)){
            throw new NoSuchElementException("KeyError: " + dependent_var);
        }
        List<Double> yList = dataMap.get(dependent_var);
        // 检查依赖变量中数据是否足够
        if(yList.size() < 2){
            throw new IllegalArgumentException("Insufficient data for regression.");
        }
        // 检查依赖变量数据是否全为数字（解析时已保证）
        for(Double d : yList){
            if(d == null){
                throw new IllegalArgumentException("Dependent variable contains non-numeric data.");
            }
        }

        // 从字符串中解析自变量列表（以逗号分隔）
        String[] indepVars = independent_vars_str.split(",");
        List<String> indepVarList = new ArrayList<>();
        for(String s : indepVars){
            indepVarList.add(s.trim());
        }

        int n = yList.size(); // 样本数量
        int p = indepVarList.size(); // 自变量个数

        // 构造X矩阵，第一列是常数项
        double[][] X = new double[n][p+1];
        double[] y = new double[n];
        for(int i=0; i<n; i++){
            X[i][0] = 1.0; // 常数项
            for(int j=0; j<p; j++){
                String key = indepVarList.get(j);
                if(!dataMap.containsKey(key)){
                    throw new NoSuchElementException("KeyError: " + key);
                }
                List<Double> col = dataMap.get(key);
                X[i][j+1] = col.get(i); // 假设每个列表长度一致
            }
            y[i] = yList.get(i);
        }

        // 调用Apache Commons Math实现OLS回归
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        // 使用我们手动添加的常数项，因此设置不自动截距
        regression.setNoIntercept(false);
        regression.newSampleData(y, X);
        double rSquared = regression.calculateRSquared();
        double[] params = regression.estimateRegressionParameters();

        // 构造回归系数字典，第一个参数为常数项
        Map<String, Double> coefMap = new LinkedHashMap<>();
        coefMap.put("const", params[0]);
        for(int j=0; j<p; j++){
            coefMap.put(indepVarList.get(j), params[j+1]);
        }

        // 返回格式与python中一致
        return "R-squared: " + rSquared + ", Coefficients: " + coefMap.toString();
    }
}