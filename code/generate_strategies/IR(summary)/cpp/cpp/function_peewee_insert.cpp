#include <iostream>
#include <sqlite3.h>
#include <string>
#include <stdexcept>
#include <filesystem>
#include <cctype>
#include <algorithm>
class PeeweeInsert {
public:
    
    std::string insert_record(const std::string& db_path, const std::string& name, int age) {
        sqlite3* db;
        char* error_message = nullptr;
        int rc;
        std::string result = "Insert Failed";
        
   
        rc = sqlite3_open(db_path.c_str(), &db);
        if (rc != SQLITE_OK) {
            sqlite3_close(db);
            return result;
        }
        
    
        const char* create_table_sql = 
            "CREATE TABLE IF NOT EXISTS TestModel ("
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"
            "name TEXT,"
            "age INTEGER"
            ");";
            
        rc = sqlite3_exec(db, create_table_sql, nullptr, nullptr, &error_message);
        if (rc != SQLITE_OK) {
            sqlite3_free(error_message);
            sqlite3_close(db);
            return result;
        }
        
     
        sqlite3_stmt* stmt;
        const char* insert_sql = "INSERT INTO TestModel (name, age) VALUES (?, ?);";
        
        rc = sqlite3_prepare_v2(db, insert_sql, -1, &stmt, nullptr);
        if (rc != SQLITE_OK) {
            sqlite3_close(db);
            return result;
        }
        
     
        sqlite3_bind_text(stmt, 1, name.c_str(), -1, SQLITE_STATIC);
        sqlite3_bind_int(stmt, 2, age);
        
      
        rc = sqlite3_step(stmt);
        if (rc == SQLITE_DONE) {
       
            sqlite3_int64 record_id = sqlite3_last_insert_rowid(db);
            result = std::to_string(record_id);
        }
        
   
        sqlite3_finalize(stmt);
        sqlite3_close(db);
        
        return result;
    }
};