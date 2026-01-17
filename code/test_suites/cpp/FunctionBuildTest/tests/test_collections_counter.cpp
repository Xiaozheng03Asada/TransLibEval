#include <gtest/gtest.h>
#include "../src/function_collections_counter.cpp"
 
TEST(TestCounterCalculator, count_single_element) {
    CounterCalculator calc;
    ASSERT_EQ(calc.count_elements("apple"), "apple:1");
}

TEST(TestCounterCalculator, count_multiple_elements) {
    CounterCalculator calc;
    ASSERT_EQ(calc.count_elements("apple orange apple banana"), "apple:2, orange:1, banana:1");
}

TEST(TestCounterCalculator, empty_input) {
    CounterCalculator calc;
    ASSERT_EQ(calc.count_elements(""), "failed");
}

TEST(TestCounterCalculator, no_repeat_elements) {
    CounterCalculator calc;
    ASSERT_EQ(calc.count_elements("apple orange banana"), "apple:1, orange:1, banana:1");
}

TEST(TestCounterCalculator, non_numeric) {
    CounterCalculator calc;
    std::string result = calc.count_elements("a b c a b c");
    ASSERT_NE(result.find("a:2"), std::string::npos);
    ASSERT_NE(result.find("b:2"), std::string::npos);
    ASSERT_NE(result.find("c:2"), std::string::npos);
}