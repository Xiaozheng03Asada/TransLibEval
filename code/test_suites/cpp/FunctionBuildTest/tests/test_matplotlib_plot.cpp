#include <gtest/gtest.h>
#include "../src/function_matplotlib_plot.cpp"

class TestMatplotlibPlot : public ::testing::Test {
};

TEST_F(TestMatplotlibPlot, TestValidInput) {
    std::string result = BarPlotGenerator::create_bar_plot("A,B,C", "10,20,30");
    EXPECT_EQ(result, "Plot saved as 'bar_plot.png'.");
}

TEST_F(TestMatplotlibPlot, TestEmptyInput) {
    std::string result = BarPlotGenerator::create_bar_plot("", "");
    EXPECT_EQ(result, "Error: Categories and values cannot be empty.");
}

TEST_F(TestMatplotlibPlot, TestMismatchedLengths) {
    std::string result = BarPlotGenerator::create_bar_plot("A,B", "10");
    EXPECT_EQ(result, "Error: Categories and values must have the same length.");
}

TEST_F(TestMatplotlibPlot, TestNonStringCategories) {
    std::string result = BarPlotGenerator::create_bar_plot("1,2,3", "10,20,30");
    EXPECT_EQ(result, "Plot saved as 'bar_plot.png'.");
}

TEST_F(TestMatplotlibPlot, TestNonNumericValues) {
    std::string result = BarPlotGenerator::create_bar_plot("A,B,C", "10,'20',30");
    EXPECT_EQ(result, "Error: All values must be numbers.");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}