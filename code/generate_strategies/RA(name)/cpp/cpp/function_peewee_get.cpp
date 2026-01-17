#include <iostream>
#include <sqlite3.h>
#include <string>
#include <vector>
#include <filesystem>
class PeeweeExecutor {
public:
   
    std::string get_name_by_id(const std::string& db_path, int record_id) {
        sqlite3* db;
        sqlite3_stmt* stmt;
        int rc;
        std::string result = "Not Found"; 

      
        rc = sqlite3_open(db_path.c_str(), &db);
        if (rc != SQLITE_OK) {
            sqlite3_close(db);
            return "Error: " + std::string(sqlite3_errmsg(db));
        }

     
        const char* create_table_sql = 
            "CREATE TABLE IF NOT EXISTS testmodel ("
            "id INTEGER PRIMARY KEY,"
            "name TEXT"
            ");";
        
        rc = sqlite3_exec(db, create_table_sql, nullptr, nullptr, nullptr);
        if (rc != SQLITE_OK) {
            sqlite3_close(db);
            return "Error: " + std::string(sqlite3_errmsg(db));
        }

    
        const char* check_sql = "SELECT COUNT(*) FROM testmodel WHERE id = 1;";
        rc = sqlite3_prepare_v2(db, check_sql, -1, &stmt, nullptr);
        if (rc != SQLITE_OK) {
            sqlite3_close(db);
            return "Error: " + std::string(sqlite3_errmsg(db));
        }

        rc = sqlite3_step(stmt);
        int record_count = 0;
        if (rc == SQLITE_ROW) {
            record_count = sqlite3_column_int(stmt, 0);
        }
        sqlite3_finalize(stmt);

    
        if (record_count == 0) {
            const char* insert_sql = 
                "INSERT INTO testmodel (id, name) VALUES (1, 'Alice');"
                "INSERT INTO testmodel (id, name) VALUES (2, 'Bob');";
            
            rc = sqlite3_exec(db, insert_sql, nullptr, nullptr, nullptr);
            if (rc != SQLITE_OK) {
                sqlite3_close(db);
                return "Error: " + std::string(sqlite3_errmsg(db));
            }
        }

  
        std::string query = "SELECT name FROM testmodel WHERE id = ?;";
        rc = sqlite3_prepare_v2(db, query.c_str(), -1, &stmt, nullptr);
        if (rc != SQLITE_OK) {
            sqlite3_close(db);
            return "Error: " + std::string(sqlite3_errmsg(db));
        }

        sqlite3_bind_int(stmt, 1, record_id);
        rc = sqlite3_step(stmt);

        if (rc == SQLITE_ROW) {
            
            result = reinterpret_cast<const char*>(sqlite3_column_text(stmt, 0));
        }

    
        sqlite3_finalize(stmt);
        sqlite3_close(db);

        return result;
    }
};