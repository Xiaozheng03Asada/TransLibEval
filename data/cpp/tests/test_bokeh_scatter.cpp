#include <gtest/gtest.h>
#include "../src/function_bokeh_scatter.cpp"

class TestBokehScatterPlot : public ::testing::Test {
protected:
    Plotter plotter;
};

TEST_F(TestBokehScatterPlot, ScatterPlotCreation) {
    std::string result = plotter.create_scatter_plot("[1, 2, 3]", "[4, 5, 6]");
    EXPECT_EQ(result, "Plot displayed.");
}

TEST_F(TestBokehScatterPlot, EmptyInput) {
    std::string result = plotter.create_scatter_plot("[]", "[]");
    EXPECT_EQ(result, "Error: Invalid input format.");
}

TEST_F(TestBokehScatterPlot, InvalidLength) {
    std::string result = plotter.create_scatter_plot("[1, 2]", "[3]");
    EXPECT_EQ(result, "Error: x_values and y_values must have the same length.");
}

TEST_F(TestBokehScatterPlot, CustomTitle) {
    std::string result = plotter.create_scatter_plot("[1, 2, 3]", "[4, 5, 6]", "Custom Scatter Plot Title");
    EXPECT_EQ(result, "Plot displayed.");
}

TEST_F(TestBokehScatterPlot, NegativeValues) {
    std::string result = plotter.create_scatter_plot("[-1, -2, -3]", "[-4, -5, -6]");
    EXPECT_EQ(result, "Plot displayed.");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}