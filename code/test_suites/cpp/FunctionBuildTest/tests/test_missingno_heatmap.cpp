#include <gtest/gtest.h>
#include "../src/function_missingno_heatmap.cpp"

class TestDataHeatmap : public ::testing::Test {
protected:
    DataHeatmap data_heatmap;
};

TEST_F(TestDataHeatmap, TestValidCSV) {
    std::string csv_data = "A,B,C\n1,2,3\n4,5,6\n7,8,9";
    EXPECT_EQ(data_heatmap.generate_heatmap(csv_data), "Heatmap generated successfully");
}

TEST_F(TestDataHeatmap, TestEmptyCSV) {
    std::string csv_data = "";
    EXPECT_EQ(data_heatmap.generate_heatmap(csv_data), "No data in the file");
}

TEST_F(TestDataHeatmap, TestMissingValues) {
    std::string csv_data = "A,B,C\n1,2,\n4,,6\n,8,9";
    EXPECT_EQ(data_heatmap.generate_heatmap(csv_data), "Heatmap generated successfully");
}

TEST_F(TestDataHeatmap, TestOtherError) {
    std::string csv_data = "A,B\nInvalid,Data,Extra\nMore,Invalid";
    std::string result = data_heatmap.generate_heatmap(csv_data);
    EXPECT_TRUE(result.find("An error occurred") != std::string::npos);
}

TEST_F(TestDataHeatmap, TestSingleColumnCSV) {
    std::string csv_data = "A\n1\n2\n3\n4\n5";
    EXPECT_EQ(data_heatmap.generate_heatmap(csv_data), "Heatmap generated successfully");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}