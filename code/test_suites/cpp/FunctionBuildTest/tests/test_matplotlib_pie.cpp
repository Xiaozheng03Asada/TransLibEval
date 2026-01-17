#include <gtest/gtest.h>
#include "../src/function_matplotlib_pie.cpp"

class TestPieChartGenerator : public ::testing::Test {
};

TEST_F(TestPieChartGenerator, TestValidInput) {
    std::string result = PieChartGenerator::create_pie_chart("A,B,C", "30,40,30");
    EXPECT_EQ(result, "Pie chart saved as 'pie_chart.png'.");
}

TEST_F(TestPieChartGenerator, TestEmptyInput) {
    std::string result = PieChartGenerator::create_pie_chart("", "");
    EXPECT_EQ(result, "Error: labels and sizes cannot be empty.");
}

TEST_F(TestPieChartGenerator, TestMismatchedLengths) {
    std::string result = PieChartGenerator::create_pie_chart("A,B", "30");
    EXPECT_EQ(result, "Error: labels and sizes must have the same length.");
}

TEST_F(TestPieChartGenerator, TestNonStringLabels) {
    std::string result = PieChartGenerator::create_pie_chart("1,B,C", "30,40,30");
    EXPECT_EQ(result, "Pie chart saved as 'pie_chart.png'.");
}

TEST_F(TestPieChartGenerator, TestNegativeSize) {
    std::string result = PieChartGenerator::create_pie_chart("A,B,C", "30,-40,30");
    EXPECT_EQ(result, "Error: All sizes must be non-negative numbers.");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}