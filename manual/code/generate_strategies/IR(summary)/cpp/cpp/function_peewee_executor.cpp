#include <iostream>
#include <sqlite3.h>
#include <string>
#include <vector>
#include <filesystem>
class PeeweeExecutor {
public:
   
    std::string execute_query(const std::string& db_path, const std::string& query) {
        sqlite3* db;
        sqlite3_stmt* stmt;
        int rc;
        std::string result = "0"; 

   
        rc = sqlite3_open(db_path.c_str(), &db);
        if (rc != SQLITE_OK) {
            std::string error_msg = "Error opening database: " + std::string(sqlite3_errmsg(db));
            sqlite3_close(db);
            throw std::runtime_error(error_msg);
        }

       
        const char* create_table_sql = "CREATE TABLE IF NOT EXISTS testmodel (name TEXT);";
        rc = sqlite3_exec(db, create_table_sql, nullptr, nullptr, nullptr);
        if (rc != SQLITE_OK) {
            std::string error_msg = "Error creating table: " + std::string(sqlite3_errmsg(db));
            sqlite3_close(db);
            throw std::runtime_error(error_msg);
        }

       
        rc = sqlite3_prepare_v2(db, query.c_str(), -1, &stmt, nullptr);
        if (rc != SQLITE_OK) {
            std::string error_msg = "Invalid SQL: " + std::string(sqlite3_errmsg(db));
            sqlite3_close(db);
            throw std::runtime_error(error_msg);
        }

       
        rc = sqlite3_step(stmt);
        
   
        if (rc == SQLITE_ROW) {
   
            if (sqlite3_column_type(stmt, 0) != SQLITE_NULL) {
                if (sqlite3_column_type(stmt, 0) == SQLITE_INTEGER) {
                    result = std::to_string(sqlite3_column_int(stmt, 0));
                } else if (sqlite3_column_type(stmt, 0) == SQLITE_TEXT) {
                    result = std::string(reinterpret_cast<const char*>(sqlite3_column_text(stmt, 0)));
                } else {
                    result = std::to_string(sqlite3_column_double(stmt, 0));
                }
            }
        }

        sqlite3_finalize(stmt);
        sqlite3_close(db);

        return result;
    }
};