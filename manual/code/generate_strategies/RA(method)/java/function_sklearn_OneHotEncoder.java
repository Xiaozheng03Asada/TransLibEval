package com.example;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class OneHotEncoderFunction {
    public String test_one_hot_encoding(String data) {
        // 自定义异常类，移入方法内部
        class TypeError extends RuntimeException {
            public TypeError(String message) {
                super(message);
            }
        }

        class IndexError extends RuntimeException {
            public IndexError(String message) {
                super(message);
            }
        }

        // 检查输入是否为 null（对应 Python 中非字符串类型）
        if (data == null) {
            throw new TypeError("Input data must be a string.");
        }

        // 检查数据结构是否符合：至少包含两个部分
        String[] parts = data.split(" ");
        if (parts.length < 2) {
            throw new IndexError("The input data has incorrect structure.");
        }

        String categoricalData;
        int numericData;
        try {
            // 逐行翻译：先取出分类数据，再转 int 得到数值数据
            categoricalData = parts[0];
            numericData = Integer.parseInt(parts[1]);
        } catch (Exception e) {
            throw new IndexError("Data structure is incorrect. Maybe missing categorical or numeric part.");
        }

        // 使用 Apache Commons Math 第三方依赖模拟 OneHotEncoder 功能
        // 这里构造一个 1x1 矩阵，其中值始终为 1.0，模拟对唯一类别做 one-hot 编码
        RealMatrix encodedMatrix = MatrixUtils.createRealMatrix(new double[][]{{1.0}});
        double encodedValue = encodedMatrix.getEntry(0, 0);

        // 拼接最终字符串，格式为 "数字,编码后的数值"
        String finalData = numericData + "," + encodedValue;
        return finalData;
    }
}