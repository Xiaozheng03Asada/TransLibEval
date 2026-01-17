#include "gtest/gtest.h"
#include "../src/function_patsy_Term.cpp"

class TestGenerateAndConvertTerm : public ::testing::Test {
protected:
    PatsyTerm term;
};

TEST_F(TestGenerateAndConvertTerm, test_generate_and_convert_term) {
    std::string term_str = term.generate_and_convert_term("x1 + x2 + x3");
    EXPECT_EQ(term_str, "x 1  + 2 3");
}

TEST_F(TestGenerateAndConvertTerm, test_empty_formula) {
    std::string term_str = term.generate_and_convert_term("");
    EXPECT_EQ(term_str, "");
}

TEST_F(TestGenerateAndConvertTerm, test_complex_formula_with_mixed_interactions) {
    std::string term_str_from_term = term.generate_and_convert_term("x1 * x2 + x3 / x4 - x5 + x6");
    EXPECT_EQ(term_str_from_term, "x 1  * 2+ 3/ 4- 5 6");
}

TEST_F(TestGenerateAndConvertTerm, test_formula_with_interaction) {
    std::string term_str_from_term = term.generate_and_convert_term("x1 * x2 + x3");
    EXPECT_EQ(term_str_from_term, "x 1  * 2+ 3");
}

TEST_F(TestGenerateAndConvertTerm, test_multiple_interaction_terms) {
    std::string term_str_from_term = term.generate_and_convert_term("x1 * x2 + x3 / x4 + x5");
    EXPECT_EQ(term_str_from_term, "x 1  * 2+ 3/ 4 5");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}