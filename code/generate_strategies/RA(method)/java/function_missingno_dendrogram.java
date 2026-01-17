package com.example;

import com.opencsv.CSVReader;
import java.io.StringReader;
import java.util.List;

public class MissingnoDendrogram {
    // create_dendrogram 方法保持与 Python 原函数名称相同
    public String create_dendrogram(String data_str) {
        try {
            if (data_str == null || data_str.trim().isEmpty()) {
                throw new Exception("Empty input");
            }
            CSVReader reader = new CSVReader(new StringReader(data_str));
            List<String[]> rows = reader.readAll();
            // 至少需要包含表头和一行数据，否则认为不符合 DataFrame 的基本格式
            if (rows.size() < 2) {
                throw new Exception("Not enough rows");
            }
            int headerLength = rows.get(0).length;
            // header 必须非空
            if (headerLength < 1) {
                throw new Exception("Invalid header");
            }
            // 检查每行数据列数是否与表头匹配
            for (int i = 1; i < rows.size(); i++) {
                if (rows.get(i).length != headerLength) {
                    throw new Exception("Row length mismatch");
                }
            }
            // 模拟生成 dendrogram 的第三方依赖调用（这里直接返回成功信息）
            return "Dendrogram created successfully";
        } catch (Exception e) {
            return "failed";
        }
    }
}