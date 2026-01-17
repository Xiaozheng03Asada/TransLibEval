#include "gtest/gtest.h"
#include <string>
#include "../src/function_patsy_ModelDesc_from_formula.cpp"

class TestPatsyParser : public ::testing::Test {
protected:
    PatsyParser parser;
};

TEST_F(TestPatsyParser, test_simple_formula) {
    std::string formula = "y ~ x1 + x2";
    std::string result = parser.parse_formula(formula);
    EXPECT_NE(result.find("\"lhs_terms\":1"), std::string::npos);
    EXPECT_NE(result.find("\"rhs_terms\":3"), std::string::npos);
}

TEST_F(TestPatsyParser, test_formula_with_no_response) {
    std::string formula = "~ x1 + x2";
    std::string result = parser.parse_formula(formula);
    EXPECT_NE(result.find("\"lhs_terms\":0"), std::string::npos);
    EXPECT_NE(result.find("\"rhs_terms\":3"), std::string::npos);
}

TEST_F(TestPatsyParser, test_invalid_formula) {
    std::string formula = "y ~ x1 +";
    std::string result = parser.parse_formula(formula);
    EXPECT_EQ(result, "Error: Failed to parse formula.");
}

TEST_F(TestPatsyParser, test_non_string_input) {
    std::string formula = ""; 
    std::string result = parser.parse_formula(formula);
    EXPECT_EQ(result, "Error: Formula must be a string.");
}

TEST_F(TestPatsyParser, test_formula_no_intercept) {
    std::string formula = "y ~ x1 + x2 - 1";
    std::string result = parser.parse_formula(formula);
    EXPECT_NE(result.find("\"rhs_terms\":2"), std::string::npos);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}