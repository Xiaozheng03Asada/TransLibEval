
#include <gtest/gtest.h>
#include "../src/function_collections_ordereddict.cpp"

class TestOrderedDictCalculator : public testing::Test {
protected:
    OrderedDictCalculator calc;
};

TEST_F(TestOrderedDictCalculator, SingleKeyValuePair) {
    std::string result = calc.process_key_value_pairs(std::string("apple:1"));
    EXPECT_EQ(result, "apple:1");
}

TEST_F(TestOrderedDictCalculator, MultipleKeyValuePairs) {
    std::string result = calc.process_key_value_pairs(std::string("apple:1, banana:2, cherry:3"));
    EXPECT_EQ(result, "apple:1, banana:2, cherry:3");
}

TEST_F(TestOrderedDictCalculator, EmptyInput) {
    std::string result = calc.process_key_value_pairs(std::string(""));
    EXPECT_EQ(result, "failed");
}

TEST_F(TestOrderedDictCalculator, NoKeyValuePair) {
    std::string result = calc.process_key_value_pairs(std::string("apple:1, , cherry:3"));
    EXPECT_EQ(result, "apple:1, cherry:3");
}

TEST_F(TestOrderedDictCalculator, NonNumeric) {
    std::string result = calc.process_key_value_pairs(std::string("apple:one, banana:two"));
    ASSERT_TRUE(!result.empty());
}


int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}