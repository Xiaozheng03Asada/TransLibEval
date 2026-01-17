#include <gtest/gtest.h>
#include "../src/function_lightgbm_feature_importance.cpp"

class TestXGBoostFeatureImportance : public ::testing::Test {
protected:
    FeatureImportance feature_importance;
};

TEST_F(TestXGBoostFeatureImportance, FeatureImportanceLength) {
    std::string input_str = "100,5";
    std::string result = feature_importance.quick_sort_from_string(input_str);
    
    int count = 1;
    for (char c : result) {
        if (c == ',') count++;
    }
    EXPECT_EQ(count, 5);
}

TEST_F(TestXGBoostFeatureImportance, FeatureImportanceNonNegative) {
    std::string input_str = "100,5";
    std::string result = feature_importance.quick_sort_from_string(input_str);
    
    std::stringstream ss(result);
    std::string token;
    while (std::getline(ss, token, ',')) {
        EXPECT_GE(std::stoi(token), 0);
    }
}

TEST_F(TestXGBoostFeatureImportance, FeatureImportanceSum) {
    std::string input_str = "100,5";
    std::string result = feature_importance.quick_sort_from_string(input_str);
    
    int sum = 0;
    std::stringstream ss(result);
    std::string token;
    while (std::getline(ss, token, ',')) {
        sum += std::stoi(token);
    }
    EXPECT_GT(sum, 0);
}

TEST_F(TestXGBoostFeatureImportance, FeatureImportanceType) {
    std::string input_str = "100,5";
    std::string result = feature_importance.quick_sort_from_string(input_str);
    EXPECT_NO_THROW(result);
}

TEST_F(TestXGBoostFeatureImportance, FeatureImportanceLargeNumberOfFeatures) {
    std::string input_str = "100,1000";
    std::string result = feature_importance.quick_sort_from_string(input_str);
    
    int count = 1;
    for (char c : result) {
        if (c == ',') count++;
    }
    EXPECT_EQ(count, 1000);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}