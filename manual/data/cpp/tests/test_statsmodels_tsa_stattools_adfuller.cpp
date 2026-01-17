#include "gtest/gtest.h"
#include "../src/function_statsmodels_tsa_stattools_adfuller.cpp"
class ADFTest : public ::testing::Test {
protected:
    static std::string stationary_series;
    static std::string non_stationary_series;
    static std::string invalid_series;
    
    static void SetUpTestSuite() {
        stationary_series = "1,1.2,0.9,1.1,0.95,1.05,0.9,1.1,0.97,1.02";
        non_stationary_series = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15";
        invalid_series = "1,2,3"; 
    }
};

std::string ADFTest::stationary_series = "";
std::string ADFTest::non_stationary_series = "";
std::string ADFTest::invalid_series = "";

TEST_F(ADFTest, NonStationarySeries) {
    SeasonalDecomposition sd;
    std::string result = sd.perform_adf_test(non_stationary_series);
    EXPECT_NE(result.find("Non-Stationary"), std::string::npos);
}

TEST_F(ADFTest, ShortSeries) {
    SeasonalDecomposition sd;
    std::string short_series = "1,2,3";
    EXPECT_THROW(sd.perform_adf_test(short_series), std::invalid_argument);
}

TEST_F(ADFTest, InvalidInputType) {
    SeasonalDecomposition sd;
    std::string series = "1,2,3,4,5";
    EXPECT_THROW(sd.perform_adf_test(series), std::invalid_argument);
}

TEST_F(ADFTest, CustomSignificanceLevel) {
    SeasonalDecomposition sd;
    std::string result = sd.perform_adf_test(stationary_series, 0.1);
    EXPECT_NE(result.find("Stationary"), std::string::npos);
}

TEST_F(ADFTest, EmptySeries) {
    SeasonalDecomposition sd;
    std::string empty_series = "";
    EXPECT_THROW(sd.perform_adf_test(empty_series), std::invalid_argument);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}