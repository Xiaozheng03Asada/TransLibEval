#include <gtest/gtest.h>
#include "../src/function_nltk_probability.cpp"

class TestProbabilityExample : public testing::Test {
protected:
    ProbabilityExample calc;
};

TEST_F(TestProbabilityExample, ComputeFrequency) {
    std::string result = calc.compute_frequency("apple");
    EXPECT_EQ(result, "a:1, p:2, l:1, e:1");
}

TEST_F(TestProbabilityExample, ComputeFrequencyWithRepeatedChars) {
    std::string result = calc.compute_frequency("banana");
    EXPECT_EQ(result, "b:1, a:3, n:2");
}

TEST_F(TestProbabilityExample, EmptyInput) {
    std::string result = calc.compute_frequency("");
    EXPECT_EQ(result, "");
}

TEST_F(TestProbabilityExample, NonStringInput) {
    EXPECT_THROW({
        calc.compute_frequency(123);
    }, std::invalid_argument);
}

TEST_F(TestProbabilityExample, EdgeCase) {
    std::string result = calc.compute_frequency("aabbcc");
    EXPECT_EQ(result, "a:2, b:2, c:2");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}