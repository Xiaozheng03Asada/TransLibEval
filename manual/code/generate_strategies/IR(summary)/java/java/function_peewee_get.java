package com.example;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.Handle;
import java.util.Optional;

public class PeeweeExecutor {
    
    public String get_name_by_id(String db_path, int record_id) {
        String jdbcUrl = "jdbc:sqlite:" + db_path;
        
        Jdbi jdbi = Jdbi.create(jdbcUrl);
        try (Handle handle = jdbi.open()) {
            
            handle.execute("DROP TABLE IF EXISTS TestModel");
            
            handle.execute("CREATE TABLE TestModel (id INTEGER PRIMARY KEY, name TEXT)");

            
            handle.execute("INSERT INTO TestModel (id, name) VALUES (?, ?)", 1, "Alice");
            handle.execute("INSERT INTO TestModel (id, name) VALUES (?, ?)", 2, "Bob");

            
            Optional<String> result = handle.createQuery("SELECT name FROM TestModel WHERE id = ?")
                    .bind(0, record_id)
                    .mapTo(String.class)
                    .findOne();

            return result.orElse("Not Found");
        } catch (Exception e) {
            return "Not Found";
        }
    }
}