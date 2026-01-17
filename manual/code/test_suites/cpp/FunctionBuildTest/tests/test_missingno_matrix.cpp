#include <gtest/gtest.h>
#include "../src/function_missingno_matrix.cpp"

class TestDataMissingVisualizer : public ::testing::Test {
protected:
    DataMissingVisualizer visualizer;
};

TEST_F(TestDataMissingVisualizer, test_no_missing_data) {
    std::string csv_data = "A,B,C\n1,2,3\n4,5,6\n7,8,9";
    std::string result = visualizer.visualize_missing_data(csv_data);
    EXPECT_TRUE(result.length() > 0);
    EXPECT_TRUE(result.find("Error:") == std::string::npos);
}

TEST_F(TestDataMissingVisualizer, test_some_missing_data) {
    std::string csv_data = "A,B,C\n1,2,\n4,,6\n,8,9";
    std::string result = visualizer.visualize_missing_data(csv_data);
    EXPECT_TRUE(result.length() > 0);
    EXPECT_TRUE(result.find("Error:") == std::string::npos);
}

TEST_F(TestDataMissingVisualizer, test_all_missing_column) {
    std::string csv_data = "A,B,C\n,,\n,,\n,,";
    std::string result = visualizer.visualize_missing_data(csv_data);
    EXPECT_TRUE(result.length() > 0);
    EXPECT_TRUE(result.find("Error:") == std::string::npos);
}

TEST_F(TestDataMissingVisualizer, test_single_column) {
    std::string csv_data = "A\n1\n\n3\n4";
    std::string result = visualizer.visualize_missing_data(csv_data);
    EXPECT_TRUE(result.length() > 0);
    EXPECT_TRUE(result.find("Error:") == std::string::npos);
}

TEST_F(TestDataMissingVisualizer, test_empty_csv) {
    std::string csv_data = "A,B,C\n";
    std::string result = visualizer.visualize_missing_data(csv_data);
    EXPECT_TRUE(result.length() > 0);
    EXPECT_TRUE(result.find("Error:") == std::string::npos);
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}