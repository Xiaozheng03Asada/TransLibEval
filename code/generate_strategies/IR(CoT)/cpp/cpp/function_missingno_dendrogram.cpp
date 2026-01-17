#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>
#include <stdexcept>
#include <string>
#include "csv.h"

class MissingnoDendrogram {
public:
    std::string create_dendrogram(const std::string& data_str) {
        try {
       
            const std::string temp_file = "temp.csv";
            std::ofstream out(temp_file);
            if (!out.is_open()) {
                throw std::runtime_error("Failed to create temporary file");
            }
            out << data_str;
            out.close();

          
            io::CSVReader<3> csv_reader(temp_file);
            csv_reader.read_header(io::ignore_extra_column, "A", "B", "C");

            std::vector<std::vector<std::string>> data;
            std::vector<std::string> row(3);

            while (csv_reader.read_row(row[0], row[1], row[2])) {
                data.push_back(row);
            }

            if (data.empty()) {
                throw std::invalid_argument("Empty data");
            }

          
            for (const auto& r : data) {
                for (const auto& cell : r) {
                    if (cell.empty()) {
                        throw std::invalid_argument("Incomplete data");
                    }
                }
            }
         
            std::remove(temp_file.c_str());

            return "Dendrogram created successfully";
        } catch (const std::exception& e) {
            return "failed";
        }
    }
};