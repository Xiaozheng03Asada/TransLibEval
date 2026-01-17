#include <gtest/gtest.h>
#include "../src/function_lightgbm_predict.cpp"


class TestXGBoostPredictor : public ::testing::Test {
protected:
    XGBoostPredictor predictor;
};


TEST_F(TestXGBoostPredictor, PredictMultipleSamples) {
    std::string input_str = "0.1,0.2,0.3,0.4,0.5;0.6,0.7,0.8,0.9,1.0";
    std::string result = predictor.quick_sort_from_string(input_str);
    

    int count = 1;
    for (char c : result) {
        if (c == ',') count++;
    }
    EXPECT_EQ(count, 2);
}


TEST_F(TestXGBoostPredictor, PredictResultType) {
    std::string input_str = "0.1,0.2,0.3,0.4,0.5";
    std::string result = predictor.quick_sort_from_string(input_str);
    
    std::stringstream ss(result);
    std::string value;
    std::getline(ss, value, ',');
    
    double result_value = std::stod(value);
    EXPECT_NO_THROW(result_value);
}


TEST_F(TestXGBoostPredictor, PredictResultRange) {
    std::string input_str = "0.1,0.2,0.3,0.4,0.5";
    std::string result = predictor.quick_sort_from_string(input_str);
    
    std::stringstream ss(result);
    std::string value;
    while (std::getline(ss, value, ',')) {
        double result_value = std::stod(value);
        EXPECT_GE(result_value, 0);
        EXPECT_LE(result_value, 1);
    }
}


TEST_F(TestXGBoostPredictor, PredictInvalidDimensionData) {
    std::string input_str = "0.1,0.2,0.3,0.4;0.1,0.2,0.3";
    EXPECT_THROW(predictor.quick_sort_from_string(input_str), std::invalid_argument);
}


TEST_F(TestXGBoostPredictor, PredictNonNumericData) {
    std::string input_str = "a,b,c,d,e;f,g,h,i,j";
    EXPECT_THROW(predictor.quick_sort_from_string(input_str), std::invalid_argument);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}