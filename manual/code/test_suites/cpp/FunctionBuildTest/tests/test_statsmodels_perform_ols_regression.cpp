#include "gtest/gtest.h"
#include "../src/function_statsmodels_perform_ols_regression.cpp"


class OLSRegressionTest : public ::testing::Test {
protected:
    static std::string data; 

    static void SetUpTestSuite() {
        data = R"({
            "X1": [1, 2, 3, 4, 5],
            "X2": [2, 4, 6, 8, 10],
            "Y": [1.2, 2.4, 3.1, 4.0, 5.1]
        })";
    }
};

std::string OLSRegressionTest::data = "";

TEST_F(OLSRegressionTest, SimpleRegression) {
    OLSRegression ols;
    std::string result = ols.perform_ols_regression(data, "Y", "X1");
    EXPECT_NE(result.find("R-squared"), std::string::npos);
    EXPECT_NE(result.find("Coefficients"), std::string::npos);
}

TEST_F(OLSRegressionTest, MultipleRegression) {
    OLSRegression ols;
    std::string result = ols.perform_ols_regression(data, "Y", "X1,X2");
    EXPECT_NE(result.find("R-squared"), std::string::npos);
}

TEST_F(OLSRegressionTest, MissingColumns) {
    OLSRegression ols;
    EXPECT_THROW(ols.perform_ols_regression(data, "Z", "X1"), std::out_of_range);
}

TEST_F(OLSRegressionTest, InsufficientData) {
    std::string insufficientData = R"({
        "X1": [1],
        "Y": [2]
    })";
    OLSRegression ols;
    EXPECT_THROW(ols.perform_ols_regression(insufficientData, "Y", "X1"), std::invalid_argument);
}

TEST_F(OLSRegressionTest, MixedDataTypes) {
    std::string mixedData = R"({
        "X1": [1, "b", 3, 4, "e"],
        "Y": [1, 2, 3, 4, 5]
    })";
    OLSRegression ols;
    EXPECT_THROW(ols.perform_ols_regression(mixedData, "Y", "X1"), std::invalid_argument);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}