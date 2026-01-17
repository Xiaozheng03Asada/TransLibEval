#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <string>
#include <map>
#include <stdexcept>
#include "csv.h" 

class DataMissingVisualizer {
public:
    std::string visualize_missing_data(const std::string& csv_data) {
        try {
       
            const std::string temp_file = "temp_data.csv";
            std::ofstream out(temp_file);
            if (!out.is_open()) {
                throw std::runtime_error("Failed to create temporary file");
            }
            out << csv_data;
            out.close();

         
            std::vector<std::string> headers;
            std::vector<std::vector<std::string>> data;
            
            
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
                throw std::runtime_error("Empty CSV data");
            }

     
            while (std::getline(infile, line)) {
                std::vector<std::string> row;
                std::stringstream ss(line);
                std::string cell;
                
                while (std::getline(ss, cell, ',')) {
                    row.push_back(cell);
                }
                
            
                while (row.size() < headers.size()) {
                    row.push_back("");
                }
                
                data.push_back(row);
            }
            infile.close();
            std::remove(temp_file.c_str());

        
            std::vector<int> non_missing_counts(headers.size(), 0);
            for (const auto& row : data) {
                for (size_t i = 0; i < headers.size(); ++i) {
                    if (i < row.size() && !row[i].empty()) {
                        non_missing_counts[i]++;
                    }
                }
            }

        
            std::stringstream result;
            result << "Missing Data Visualization:" << std::endl;
            
        
            for (size_t i = 0; i < headers.size(); ++i) {
                result << headers[i] << ": " << non_missing_counts[i] 
                       << " non-missing values out of " << data.size() 
                       << " (" << (non_missing_counts[i] * 100.0 / data.size()) << "%)" << std::endl;
            }
            
        
            int total_cells = headers.size() * data.size();
            int total_non_missing = 0;
            for (const auto& count : non_missing_counts) {
                total_non_missing += count;
            }
            
            result << "Overall: " << total_non_missing << " non-missing values out of " 
                   << total_cells << " (" << (total_non_missing * 100.0 / total_cells) << "%)";
            
          
            std::string result_string = result.str();
            
           
            return result_string;
        }
        catch (const std::exception& e) {
            return std::string("Error: ") + e.what();
        }
    }
};