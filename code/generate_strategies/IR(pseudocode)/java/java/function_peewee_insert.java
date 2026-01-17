package com.example;

import org.sql2o.Sql2o;
import org.sql2o.Connection;



public class PeeweeInsert {
    
    public String insert_record(String db_path, String name, int age) {
        String result;
        
        Sql2o sql2o = new Sql2o("jdbc:sqlite:" + db_path, null, null);
        
        try (Connection con = sql2o.open()) {
            
            String createTableSQL = "CREATE TABLE IF NOT EXISTS TestModel ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name TEXT, "
                    + "age INTEGER)";
            con.createQuery(createTableSQL).executeUpdate();

            
            String insertSQL = "INSERT INTO TestModel (name, age) VALUES (:name, :age)";
            int insertedId = con.createQuery(insertSQL, true)
                    .addParameter("name", name)
                    .addParameter("age", age)
                    .executeUpdate()
                    .getKey(Integer.class);
            result = String.valueOf(insertedId);
        } catch (Exception e) {
            
            result = "Insert Failed";
        }
        return result;
    }
}