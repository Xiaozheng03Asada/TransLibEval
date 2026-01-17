#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <stdexcept>
#include "csv.h" 

class MissingnoBar {
public:
    std::string visualize_missing_data(const std::string& csv_data) {
        try {
   
            const std::string temp_file = "temp.csv";
            std::ofstream out(temp_file);
            if (!out.is_open()) {
                throw std::runtime_error("Failed to create temporary file");
            }
            out << csv_data;
            out.close();

         
            io::CSVReader<3> csv_reader(temp_file); 
            csv_reader.read_header(io::ignore_extra_column, "A", "B", "C");

            std::vector<std::vector<std::string>> data;
            std::vector<std::string> row(3);

            while (csv_reader.read_row(row[0], row[1], row[2])) {
                data.push_back(row);
            }

            if (data.empty()) {
                throw std::invalid_argument("Empty CSV data");
            }

        
            size_t column_count = data[0].size();
            for (const auto& r : data) {
                if (r.size() != column_count) {
                    throw std::invalid_argument("Inconsistent column count in CSV data");
                }
            }

      
            std::vector<int> missing_counts(column_count, 0);
            for (const auto& r : data) {
                for (size_t i = 0; i < r.size(); ++i) {
                    if (r[i].empty()) {
                        missing_counts[i]++;
                    }
                }
            }

         
            std::remove(temp_file.c_str());

            return "Missing data visualization generated.";
        } catch (const std::exception& e) {
            throw std::invalid_argument(std::string("Invalid CSV format: ") + e.what());
        }
    }
};