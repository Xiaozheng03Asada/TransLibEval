#include <gtest/gtest.h>

#include "../src/function_funcy_distinct.cpp" 

TEST(TestFuncyDistinct, remove_duplicates) {
    ListProcessor processor;
    std::string result = processor.process_list("1,2,3,1,4,2,5");
    EXPECT_EQ(result, "1,2,3,4,5");
}

TEST(TestFuncyDistinct, all_unique_elements) {
    ListProcessor processor;
    std::string result = processor.process_list("1,2,3,4,5");
    EXPECT_EQ(result, "1,2,3,4,5");
}

TEST(TestFuncyDistinct, mixed_types) {
    ListProcessor processor;
    std::string result = processor.process_list("1,apple,1,banana,apple,3.14,3.14");
    EXPECT_EQ(result, "1,apple,banana,3.14");
}

TEST(TestFuncyDistinct, mixed_case_strings) {
    ListProcessor processor;
    std::string result = processor.process_list("apple,Apple,banana,apple,Banana");
    EXPECT_EQ(result, "apple,Apple,banana,Banana");
}

TEST(TestFuncyDistinct, large_list_with_duplicates) {
    ListProcessor processor;
    std::string input_str;
    for (int i = 0; i < 1000; ++i) {
        input_str += "1,";
    }
    for (int i = 0; i < 1000; ++i) {
        input_str += "2,";
    }
    for (int i = 0; i < 1000; ++i) {
        input_str += "3,";
    }
    if (!input_str.empty() && input_str.back() == ',') {
        input_str.pop_back();
    }
    std::string result = processor.process_list(input_str);
    EXPECT_EQ(result, "1,2,3");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}