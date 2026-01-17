#include <gtest/gtest.h>
#include "../src/function_nltk_jaccard_distance.cpp"

class TestJaccardExample : public testing::Test {
protected:
    JaccardExample calc;
};

TEST_F(TestJaccardExample, IdenticalStrings) {
    float result = calc.compute_jaccard_distance("apple", "apple");
    EXPECT_FLOAT_EQ(result, 0.0);
}

TEST_F(TestJaccardExample, DifferentStrings) {
    float result = calc.compute_jaccard_distance("apple", "orange");
    EXPECT_GT(result, 0.0);
}

TEST_F(TestJaccardExample, EmptyStrings) {
    float result = calc.compute_jaccard_distance("", "");
    EXPECT_FLOAT_EQ(result, 0.0);
}

TEST_F(TestJaccardExample, PartialOverlap) {
    float result = calc.compute_jaccard_distance("apple", "app");
    EXPECT_GT(result, 0.0);
}

TEST_F(TestJaccardExample, NonStringInput) {
    EXPECT_THROW({
        calc.compute_jaccard_distance("apple", 123);
    }, std::invalid_argument);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}