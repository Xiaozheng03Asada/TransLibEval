#include <gtest/gtest.h>
#include "../src/function_bokeh_circle.cpp"

class TestBokehCirclePlot : public ::testing::Test {
protected:
    Plotter plotter;
};

TEST_F(TestBokehCirclePlot, CirclePlotCreation) {
    std::string result = plotter.create_circle_plot("[1, 2, 3]", "[4, 5, 6]");
    EXPECT_EQ(result, "Plot displayed.");
}

TEST_F(TestBokehCirclePlot, EmptyInput) {
    std::string result = plotter.create_circle_plot("[]", "[]");
    EXPECT_EQ(result, "Error: Invalid input format.");
}

TEST_F(TestBokehCirclePlot, InvalidLength) {
    std::string result = plotter.create_circle_plot("[1, 2]", "[3]");
    EXPECT_EQ(result, "Error: x_values and y_values must have the same length.");
}

TEST_F(TestBokehCirclePlot, CustomTitle) {
    std::string result = plotter.create_circle_plot("[1, 2, 3]", "[4, 5, 6]", 10, "Custom Circle Plot Title");
    EXPECT_EQ(result, "Plot displayed.");
}

TEST_F(TestBokehCirclePlot, CustomRadius) {
    std::string result = plotter.create_circle_plot("[1, 2, 3]", "[4, 5, 6]", 15);
    EXPECT_EQ(result, "Plot displayed.");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}