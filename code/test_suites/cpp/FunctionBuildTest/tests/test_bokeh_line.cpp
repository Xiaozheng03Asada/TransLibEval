#include <gtest/gtest.h>
#include "../src/function_bokeh_line.cpp"

class TestBokehLinePlot : public ::testing::Test {
protected:
    Plotter plotter;
};

TEST_F(TestBokehLinePlot, LinePlotCreation) {
    std::string result = plotter.create_line_plot("[1, 2, 3]", "[4, 5, 6]");
    EXPECT_EQ(result, "Plot displayed.");
}

TEST_F(TestBokehLinePlot, EmptyInput) {
    std::string result = plotter.create_line_plot("[]", "[]");
    EXPECT_EQ(result, "Error: Invalid input format.");
}

TEST_F(TestBokehLinePlot, InvalidLength) {
    std::string result = plotter.create_line_plot("[1, 2]", "[3]");
    EXPECT_EQ(result, "Error: x_values and y_values must have the same length.");
}

TEST_F(TestBokehLinePlot, CustomTitle) {
    std::string result = plotter.create_line_plot("[1, 2, 3]", "[4, 5, 6]", "Custom Line Plot Title");
    EXPECT_EQ(result, "Plot displayed.");
}

TEST_F(TestBokehLinePlot, NegativeValues) {
    std::string result = plotter.create_line_plot("[-1, -2, -3]", "[-4, -5, -6]");
    EXPECT_EQ(result, "Plot displayed.");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}