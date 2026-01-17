#include <gtest/gtest.h>
#include "../src/function_matplotlib_bar.cpp"

class TestBarPlotGenerator : public ::testing::Test {
};

TEST_F(TestBarPlotGenerator, TestValidInput) {
    std::string result = BarPlotGenerator::create_bar_plot("A,B,C", "10,20,30");
    EXPECT_EQ(result, "Plot saved as 'bar_plot.png'.");
}

TEST_F(TestBarPlotGenerator, TestEmptyInput) {
    std::string result = BarPlotGenerator::create_bar_plot("", "");
    EXPECT_EQ(result, "Error: Categories and values cannot be empty.");
}

TEST_F(TestBarPlotGenerator, TestMismatchedLengths) {
    std::string result = BarPlotGenerator::create_bar_plot("A,B", "10");
    EXPECT_EQ(result, "Error: Categories and values must have the same length.");
}

TEST_F(TestBarPlotGenerator, TestNonStringCategories) {
    std::string result = BarPlotGenerator::create_bar_plot("1,2,3", "10,20,30");
    EXPECT_EQ(result, "Plot saved as 'bar_plot.png'.");
}

TEST_F(TestBarPlotGenerator, TestNonNumericValues) {
    std::string result = BarPlotGenerator::create_bar_plot("A,B,C", "10,'20',30");
    EXPECT_EQ(result, "Plot saved as 'bar_plot.png'.");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}