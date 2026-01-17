#include <gtest/gtest.h>
#include "../src/function_seaborn_kdeplot.cpp"

class TestSeabornKDEPlot : public testing::Test {
protected:
    SeabornKDEPlot kde_plot;
};

TEST_F(TestSeabornKDEPlot, KDEPlotSingleData) {
    std::string data = "1.0,2.0,3.0,4.0,5.0";
    std::string result = kde_plot.generate_kdeplot(data, true, "blue");
    EXPECT_NE(result.find("'data': '1.0,2.0,3.0,4.0,5.0'"), std::string::npos);
    EXPECT_NE(result.find("'shade': True"), std::string::npos);
    EXPECT_NE(result.find("'color': 'blue'"), std::string::npos);
}

TEST_F(TestSeabornKDEPlot, KDEPlotWithDifferentColor) {
    std::string data = "1.0,2.0,3.0,4.0,5.0";
    std::string result = kde_plot.generate_kdeplot(data, true, "red");
    EXPECT_NE(result.find("'data': '1.0,2.0,3.0,4.0,5.0'"), std::string::npos);
    EXPECT_NE(result.find("'shade': True"), std::string::npos);
    EXPECT_NE(result.find("'color': 'red'"), std::string::npos);
}

TEST_F(TestSeabornKDEPlot, KDEPlotWithoutShade) {
    std::string data = "1.0,2.0,3.0,4.0,5.0";
    std::string result = kde_plot.generate_kdeplot(data, false, "green");
    EXPECT_NE(result.find("'data': '1.0,2.0,3.0,4.0,5.0'"), std::string::npos);
    EXPECT_NE(result.find("'shade': False"), std::string::npos);
    EXPECT_NE(result.find("'color': 'green'"), std::string::npos);
}

TEST_F(TestSeabornKDEPlot, KDEPlotEmptyData) {
    std::string data = "";
    EXPECT_THROW(kde_plot.generate_kdeplot(data, true, "blue"), std::invalid_argument);
}

TEST_F(TestSeabornKDEPlot, KDEPlotInvalidData) {
    std::string data = "invalid data";
    EXPECT_THROW(kde_plot.generate_kdeplot(data, true, "blue"), std::invalid_argument);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}