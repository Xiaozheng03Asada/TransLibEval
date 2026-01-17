#include "gtest/gtest.h"
#include "../src/function_statsmodels_perform_exponential_smoothing.cpp"


static const std::string valid_data = "[10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10, 10, 12, 9, 11, 10, 13, 9, 12, 10, 12, 11, 10]";

TEST(ExponentialSmoothingTest, AdditiveSeasonality) {
    ExponentialSmoothingProcessor esp;
    std::string result = esp.perform_exponential_smoothing(valid_data, 5, "add", 12);
    EXPECT_NE(result.find("Forecast"), std::string::npos);
}

TEST(ExponentialSmoothingTest, MultiplicativeSeasonality) {
    ExponentialSmoothingProcessor esp;
    std::string result = esp.perform_exponential_smoothing(valid_data, 5, "mul", 12);
    EXPECT_NE(result.find("Forecast"), std::string::npos);
}

TEST(ExponentialSmoothingTest, InputTypeError) {
    ExponentialSmoothingProcessor esp;
    EXPECT_THROW(esp.perform_exponential_smoothing(12345), std::invalid_argument);
}

TEST(ExponentialSmoothingTest, InvalidSeasonalType) {
    ExponentialSmoothingProcessor esp;
    EXPECT_THROW(esp.perform_exponential_smoothing(valid_data, 5, "invalid", 12), std::invalid_argument);
}

TEST(ExponentialSmoothingTest, InvalidForecastSteps) {
    ExponentialSmoothingProcessor esp;
    EXPECT_THROW(esp.perform_exponential_smoothing(valid_data, -1, "add", 12), std::invalid_argument);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}