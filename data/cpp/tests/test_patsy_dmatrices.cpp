#include "gtest/gtest.h"
#include <string>
#include "../src/function_patsy_dmatrices.cpp"

class TestPatsyProcessor : public ::testing::Test {
protected:
    PatsyProcessor processor;
    std::string data = R"({
            "y": [1, 2, 3, 4],
            "x1": [2, 4, 6, 8],
            "x2": [1, 3, 5, 7],
            "category": ["A", "B", "A", "B"]
        })";
};

TEST_F(TestPatsyProcessor, test_simple_formula) {
    std::string formula = "y ~ x1 + x2";
    std::string result = processor.process_formula(data, formula);
    EXPECT_NE(result.find("\"y_shape\":[4,1]"), std::string::npos);
    EXPECT_NE(result.find("\"X_shape\":[4,3]"), std::string::npos);
}

TEST_F(TestPatsyProcessor, test_interaction_formula) {
    std::string formula = "y ~ x1 * x2";
    std::string result = processor.process_formula(data, formula);
    EXPECT_NE(result.find("\"X_shape\":[4,4]"), std::string::npos);
}

TEST_F(TestPatsyProcessor, test_polynomial_formula) {
    std::string formula = "y ~ x1 + I(x1 ** 2) + I(x1 ** 3)";
    std::string result = processor.process_formula(data, formula);
    EXPECT_NE(result.find("\"X_shape\":[4,4]"), std::string::npos);
}

TEST_F(TestPatsyProcessor, test_invalid_formula) {
    std::string formula = "y ~ non_existent_column";
    std::string result = processor.process_formula(data, formula);
    EXPECT_EQ(result, "Error: invalid input");
}

TEST_F(TestPatsyProcessor, test_malformed_json) {
    std::string malformed_data = R"({"y": [1, 2, 3, 4], "x1": [2, 4, 6, 8], "x2": [1, 3, 5, 7],)";
    std::string formula = "y ~ x1 + x2";
    std::string result = processor.process_formula(malformed_data, formula);
    EXPECT_EQ(result, "Error: invalid input");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}