#include <gtest/gtest.h>
#include "../src/function_matplotlib_scatter.cpp"

class TestScatterPlotGenerator : public ::testing::Test {
};

TEST_F(TestScatterPlotGenerator, TestValidInput) {
    std::string result = ScatterPlotGenerator::create_scatter_plot("1,2,3", "4,5,6");
    EXPECT_EQ(result, "Plot saved as 'scatter_plot.png'.");
}

TEST_F(TestScatterPlotGenerator, TestEmptyInput) {
    std::string result = ScatterPlotGenerator::create_scatter_plot("", "");
    EXPECT_EQ(result, "Error: x_values and y_values cannot be empty.");
}

TEST_F(TestScatterPlotGenerator, TestMismatchedLengths) {
    std::string result = ScatterPlotGenerator::create_scatter_plot("1,2", "3");
    EXPECT_EQ(result, "Error: x_values and y_values must have the same length.");
}

TEST_F(TestScatterPlotGenerator, TestNonNumericValues) {
    std::string result = ScatterPlotGenerator::create_scatter_plot("1,'2',3", "4,5,6");
    EXPECT_EQ(result, "Error: All x and y values must be numbers.");
}

TEST_F(TestScatterPlotGenerator, TestLargeInput) {
    // 生成1000个值
    std::ostringstream x_stream, y_stream;
    for (int i = 0; i < 1000; ++i) {
        if (i > 0) {
            x_stream << ",";
            y_stream << ",";
        }
        x_stream << i;
        y_stream << (i * 2);
    }
    
    std::string result = ScatterPlotGenerator::create_scatter_plot(x_stream.str(), y_stream.str());
    EXPECT_EQ(result, "Plot saved as 'scatter_plot.png'.");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}