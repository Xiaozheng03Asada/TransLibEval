#include <gtest/gtest.h>
#include "../src/function_bokeh_bar.cpp"

class TestBokehBarChart : public ::testing::Test {
protected:
    Plotter plotter;
};

TEST_F(TestBokehBarChart, BarChartCreation) {
    std::string result = plotter.create_bar_chart("[\"A\", \"B\", \"C\"]", "[4, 5, 6]");
    EXPECT_EQ(result, "Plot displayed.");
}

TEST_F(TestBokehBarChart, EmptyInput) {
    std::string result = plotter.create_bar_chart("[]", "[]");
    EXPECT_EQ(result, "Error: Invalid input format.");
}

TEST_F(TestBokehBarChart, InvalidLength) {
    std::string result = plotter.create_bar_chart("[1, 2]", "[3]");
    EXPECT_EQ(result, "Error: x_values and y_values must have the same length.");
}

TEST_F(TestBokehBarChart, CustomTitle) {
    std::string result = plotter.create_bar_chart("[\"A\", \"B\", \"C\"]", "[4, 5, 6]", "Custom Bar Chart Title");
    EXPECT_EQ(result, "Plot displayed.");
}

TEST_F(TestBokehBarChart, NegativeValues) {
    std::string result = plotter.create_bar_chart("[\"A\", \"B\", \"C\"]", "[-4, -5, -6]");
    EXPECT_EQ(result, "Plot displayed.");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}