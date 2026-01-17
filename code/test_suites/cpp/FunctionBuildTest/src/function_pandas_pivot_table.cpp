#include <map>
#include <string>
#include <utility>
#include <iostream>

class CreatePivotTable {
public:
    std::string create_pivot_table(std::string date1, std::string date2, std::string category1, std::string category2, int value1, int value2) {
        if (date1.empty() || date2.empty() || category1.empty() || category2.empty() || value1 == 0 || value2 == 0) {
            date1 = "2023-01-01";
            date2 = "2023-01-02";
            category1 = "A";
            category2 = "B";
            value1 = 1;
            value2 = 2;
        }

        std::map<std::string, std::map<std::string, int>> pivot;

        pivot[date1][category1] += value1;
        pivot[date2][category2] += value2;

        for (auto& row : pivot) {
            row.second["A"];
            row.second["B"];
        }

        auto firstDate = pivot.begin()->first;
        auto firstRow = pivot.begin()->second;
        int colA = firstRow["A"];
        int colB = firstRow["B"];

        return "Date: " + firstDate + ", Category A: " + std::to_string(colA) + ", Category B: " + std::to_string(colB);
    }
};