#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <stdexcept>
#include "csv.h" 

class DataHeatmap {
public:
    std::string generate_heatmap(const std::string& data) {
        try {
            if (data.empty()) {
                return "No data in the file";
            }

   
            const std::string temp_file = "temp_data.csv";
            std::ofstream out(temp_file);
            if (!out.is_open()) {
                throw std::runtime_error("Failed to create temporary file");
            }
            out << data;
            out.close();

            
            std::vector<std::string> headers;
            std::vector<std::vector<std::string>> csv_data;

          
            std::ifstream infile(temp_file);
            std::string line;
            if (std::getline(infile, line)) {
                std::stringstream ss(line);
                std::string cell;
                while (std::getline(ss, cell, ',')) {
                    headers.push_back(cell);
                }
            } else {
                std::remove(temp_file.c_str());
                return "No data in the file";
            }

        
            while (std::getline(infile, line)) {
                std::vector<std::string> row;
                std::stringstream ss(line);
                std::string cell;
                while (std::getline(ss, cell, ',')) {
                    row.push_back(cell);
                }
           
                if (row.size() > headers.size()) {
                    std::remove(temp_file.c_str());
                    throw std::runtime_error("Data row has more columns than header");
                }
                
             
                while (row.size() < headers.size()) {
                    row.push_back("");
                }
                csv_data.push_back(row);
            }
            infile.close();
            std::remove(temp_file.c_str());

            if (csv_data.empty()) {
                return "No data in the file";
            }

            size_t total_cells = headers.size() * csv_data.size();
            size_t missing_cells = 0;

            for (const auto& row : csv_data) {
                for (const auto& cell : row) {
                    if (cell.empty()) {
                        missing_cells++;
                    }
                }
            }
           
            for (size_t i = 0; i < headers.size(); ++i) {
                size_t col_missing = 0;
                for (const auto& row : csv_data) {
                    if (i < row.size() && row[i].empty()) {
                        col_missing++;
                    }
                }
            
            }

            return "Heatmap generated successfully";
        } 
        catch (const std::exception& e) {
            return std::string("An error occurred: ") + e.what();
        }
        catch (...) {
            return "An error occurred: Unknown error";
        }
    }
};