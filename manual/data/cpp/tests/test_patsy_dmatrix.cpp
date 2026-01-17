#include "gtest/gtest.h"
#include <string>
#include "../src/function_patsy_dmatrix.cpp"

class TestPatsyProcessor : public ::testing::Test {
protected:
    PatsyProcessor processor;
    std::string data = R"({
            "x": [1, 2, 3, 4],
            "y": [2, 4, 6, 8],
            "z": [5, 6, 7, 8],
            "w": [10, 15, 20, 25]
        })";
};

TEST_F(TestPatsyProcessor, test_polynomial_formula) {
    std::string formula = "x + I(x ** 2)";
    std::string result = processor.generate_matrix(data, formula);
    EXPECT_NE(result.find("\"matrix_shape\": [4, 3]"), std::string::npos);
}

TEST_F(TestPatsyProcessor, test_categorical_variable) {
    std::string formula = "C(z)";
    std::string result = processor.generate_matrix(data, formula);
    EXPECT_NE(result.find("\"matrix_shape\": [4,"), std::string::npos);
}

TEST_F(TestPatsyProcessor, test_multiple_interaction_terms) {
    std::string formula = "x * z * w";
    std::string result = processor.generate_matrix(data, formula);
    EXPECT_NE(result.find("\"matrix_shape\": [4, 8]"), std::string::npos);
}

TEST_F(TestPatsyProcessor, test_conditional_expression) {
    std::string formula = "x + (z > 6)";
    std::string result = processor.generate_matrix(data, formula);
    EXPECT_NE(result.find("\"matrix_shape\": [4, 3]"), std::string::npos);
}

TEST_F(TestPatsyProcessor, test_dummy_variable_encoding) {
    std::string formula = "C(w > 15)";
    std::string result = processor.generate_matrix(data, formula);
    EXPECT_NE(result.find("\"matrix_shape\": [4, 2]"), std::string::npos);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}