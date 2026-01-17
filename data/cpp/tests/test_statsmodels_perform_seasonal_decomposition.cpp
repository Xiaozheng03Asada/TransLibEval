#include "gtest/gtest.h"
#include "../src/function_statsmodels_perform_seasonal_decomposition.cpp"


static const std::string additive_data = "10.248357, 10.248557, 10.248757, 10.248957, 10.249157, 10.249357, 10.249557, 10.249757, 10.249957, 10.250157";

TEST(SeasonalDecompositionTest, AdditiveDecomposition) {
    SeasonalDecomposition sd;
    std::string result = sd.perform_seasonal_decomposition(additive_data, "additive", 12);
    EXPECT_NE(result.find("Trend"), std::string::npos);
    EXPECT_NE(result.find("Seasonal"), std::string::npos);
    EXPECT_NE(result.find("Residual"), std::string::npos);
}

TEST(SeasonalDecompositionTest, MultiplicativeDecomposition) {
    SeasonalDecomposition sd;
    std::string result = sd.perform_seasonal_decomposition(additive_data, "multiplicative", 12);
    EXPECT_NE(result.find("Trend"), std::string::npos);
    EXPECT_NE(result.find("Seasonal"), std::string::npos);
    EXPECT_NE(result.find("Residual"), std::string::npos);
}

TEST(SeasonalDecompositionTest, InvalidModelType) {
    SeasonalDecomposition sd;
    EXPECT_THROW(sd.perform_seasonal_decomposition(additive_data, "invalid", 12), std::invalid_argument);
}

TEST(SeasonalDecompositionTest, InvalidPeriod) {
    SeasonalDecomposition sd;
    EXPECT_THROW(sd.perform_seasonal_decomposition(additive_data, "additive", -1), std::invalid_argument);
}

TEST(SeasonalDecompositionTest, NonStringInput) {
    SeasonalDecomposition sd;
    EXPECT_THROW(sd.perform_seasonal_decomposition(12345), std::invalid_argument);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}