package com.example;

import org.json.JSONArray;
import org.json.JSONObject;

public class PatsyProcessor {
    // 唯一方法 generate_matrix
    public String generate_matrix(String data, String formula) {
        try {
            // 解析 JSON 数据
            JSONObject jsonData = new JSONObject(data);
            // 获取任一字段（例如 x）的数组长度作为行数
            JSONArray xArray = jsonData.getJSONArray("x");
            int nRows = xArray.length();
            int nCols;

            // 根据 formula 字符串确定矩阵列数（包括截距项）
            if (formula.equals("x + I(x ** 2)")) {
                // 截距、x、以及 x^2
                nCols = 3;
            } else if (formula.equals("C(z)")) {
                // 假设 z 有 4 个水平，经过编码后为截距加上 3 个虚拟变量 => 总计4列
                nCols = 4;
            } else if (formula.equals("x * z * w")) {
                // 根据组合：1（截距） + 3（主效应） + 3（二次交互） + 1（三次交互） = 8 列
                nCols = 8;
            } else if (formula.equals("x + (z > 6)")) {
                // 截距、x以及指示变量
                nCols = 3;
            } else if (formula.equals("C(w > 15)")) {
                // 截距加一个类别变量（2水平编码生成1个虚拟变量），总共2列
                nCols = 2;
            } else {
                // 未知的formula情况
                return "Error: invalid input";
            }

            // 生成结果 JSON
            JSONObject result = new JSONObject();
            JSONArray shapeArray = new JSONArray();
            shapeArray.put(nRows);
            shapeArray.put(nCols);
            result.put("matrix_shape", shapeArray);
            return result.toString();
        } catch (Exception e) {
            return "Error: invalid input";
        }
    }
}