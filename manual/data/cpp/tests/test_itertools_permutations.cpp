#include <gtest/gtest.h>
#include "../src/function_itertools_permutations.cpp"

class TestPermutations : public testing::Test {
protected:
    PermutationsProcessor processor;
};

TEST_F(TestPermutations, GetPermutationsContent) {
    std::string result = processor.get_permutations("abc");
    EXPECT_EQ(result, "abc, acb, bac, bca, cab, cba\nabc: 1, acb: 1, bac: 1, bca: 1, cab: 1, cba: 1");
}

TEST_F(TestPermutations, PermutationDuplicates) {
    std::string result = processor.get_permutations("aab");
    EXPECT_EQ(result, "aab, aab, aba, aba, baa, baa\naab: 2, aba: 2, baa: 2");
}

TEST_F(TestPermutations, InvalidTypeError) {
    std::vector<int> data{1, 2, 3};
    EXPECT_THROW(processor.get_permutations(data), std::invalid_argument);
}

TEST_F(TestPermutations, NonIterableError) {
    EXPECT_THROW(processor.get_permutations(123), std::invalid_argument);
}

TEST_F(TestPermutations, PermutationsCaseSensitivity) {
    std::string result = processor.get_permutations("Aa");
    EXPECT_EQ(result, "Aa, aA\nAa: 1, aA: 1");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}