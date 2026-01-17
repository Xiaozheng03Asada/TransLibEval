package com.example;

import org.sql2o.Sql2o;
import org.sql2o.Connection;



public class PeeweeInsert {
    // 所有功能在此唯一方法内实现
    public String insert_record(String db_path, String name, int age) {
        String result;
        // 使用 Sql2o 创建数据库连接对象，连接字符串格式为 "jdbc:sqlite:" + db_path
        Sql2o sql2o = new Sql2o("jdbc:sqlite:" + db_path, null, null);
        // 利用 try-with-resources 自动关闭连接
        try (Connection con = sql2o.open()) {
            // 创建表：对应 Python 中 database.create_tables([TestModel])
            String createTableSQL = "CREATE TABLE IF NOT EXISTS TestModel ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name TEXT, "
                    + "age INTEGER)";
            con.createQuery(createTableSQL).executeUpdate();

            // 执行插入操作：对应 Python 中 TestModel.insert(name=name, age=age)
            String insertSQL = "INSERT INTO TestModel (name, age) VALUES (:name, :age)";
            int insertedId = con.createQuery(insertSQL, true)
                    .addParameter("name", name)
                    .addParameter("age", age)
                    .executeUpdate()
                    .getKey(Integer.class);
            result = String.valueOf(insertedId);
        } catch (Exception e) {
            // 捕获异常返回 "Insert Failed"
            result = "Insert Failed";
        }
        return result;
    }
}