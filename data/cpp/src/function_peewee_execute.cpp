#include <iostream>
#include <sqlite3.h>
#include <string>
#include <algorithm>
#include <cctype>
#include <filesystem>
class PeeweeExecutor {
public:
    std::string execute_query(const std::string& db_path, const std::string& query) {
        sqlite3* db;
        char* err_msg = nullptr;
        int rc;
        int affected_rows = -1;

     
        rc = sqlite3_open(db_path.c_str(), &db);
        if (rc != SQLITE_OK) {
            sqlite3_close(db);
            return "Error opening database";
        }

     
        const char* create_table_sql = "CREATE TABLE IF NOT EXISTS test (name TEXT);";
        rc = sqlite3_exec(db, create_table_sql, nullptr, nullptr, &err_msg);
        if (rc != SQLITE_OK) {
            std::cerr << "SQL error: " << err_msg << std::endl;
            sqlite3_free(err_msg);
            sqlite3_close(db);
            return "Error creating table";
        }

 
        rc = sqlite3_exec(db, query.c_str(), nullptr, nullptr, &err_msg);
        if (rc != SQLITE_OK) {
            std::cerr << "SQL error: " << err_msg << std::endl;
            sqlite3_free(err_msg);
            sqlite3_close(db);
            return "Error executing query";
        }

        std::string query_lower = query;
        std::transform(query_lower.begin(), query_lower.end(), query_lower.begin(),
                       [](unsigned char c){ return std::tolower(c); });
        
        if (query_lower.find("create table") != std::string::npos) {
            affected_rows = -1;  
        } else {
         
            affected_rows = sqlite3_changes(db);
        }
        
      
        sqlite3_close(db);

   
        return std::to_string(affected_rows);
    }
};