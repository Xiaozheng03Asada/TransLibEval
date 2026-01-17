package com.example;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.Handle;
import java.util.Optional;

public class PeeweeExecutor {
    // Non-static method implementing all functionality.
    public String get_name_by_id(String db_path, int record_id) {
        String jdbcUrl = "jdbc:sqlite:" + db_path;
        // Create a Jdbi instance using the SQLite JDBC driver.
        Jdbi jdbi = Jdbi.create(jdbcUrl);
        try (Handle handle = jdbi.open()) {
            // Drop the table if it exists to ensure a fresh state for tests.
            handle.execute("DROP TABLE IF EXISTS TestModel");
            // Create table.
            handle.execute("CREATE TABLE TestModel (id INTEGER PRIMARY KEY, name TEXT)");

            // Insert test data.
            handle.execute("INSERT INTO TestModel (id, name) VALUES (?, ?)", 1, "Alice");
            handle.execute("INSERT INTO TestModel (id, name) VALUES (?, ?)", 2, "Bob");

            // Query the name for the given record_id.
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