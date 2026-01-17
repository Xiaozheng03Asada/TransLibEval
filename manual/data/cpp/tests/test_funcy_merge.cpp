#include <gtest/gtest.h>
#include "../src/function_funcy_merge.cpp"

class TestFuncyMerge : public ::testing::Test {
protected:
    DictMerger merger;
};

TEST_F(TestFuncyMerge, CombineDicts) {
    std::string result = merger.combine_dicts("{\"a\": 1, \"b\": 2}", "{\"b\": 3, \"c\": 4}");
    EXPECT_EQ(result, "{\"a\":1,\"b\":3,\"c\":4}");
}

TEST_F(TestFuncyMerge, OverridingKeys) {
    std::string result = merger.combine_dicts("{\"a\": 1, \"b\": 2}", "{\"a\": 3, \"b\": 4, \"c\": 5}");
    EXPECT_EQ(result, "{\"a\":3,\"b\":4,\"c\":5}");
}

TEST_F(TestFuncyMerge, EmptyDict) {
    std::string result = merger.combine_dicts("{}", "{\"a\": 1, \"b\": 2}");
    EXPECT_EQ(result, "{\"a\":1,\"b\":2}");
}

TEST_F(TestFuncyMerge, MultipleOverriding) {
    std::string dict1 = "{\"a\": 1, \"b\": 2}";
    std::string dict2 = "{\"a\": 3, \"b\": 4}";
    std::string dict3 = "{\"a\": 5, \"d\": 6}";
    std::string result = merger.combine_dicts(dict1, merger.combine_dicts(dict2, dict3));
    EXPECT_EQ(result, "{\"a\":5,\"b\":4,\"d\":6}");
}

TEST_F(TestFuncyMerge, InvalidInput) {
    std::string result = merger.combine_dicts("12345", "{\"a\": 1, \"b\": 2}");
    EXPECT_EQ(result, "Error: input is not a dictionary");
}

TEST_F(TestFuncyMerge, InvalidJson) {
    std::string result = merger.combine_dicts("{invalid_json}", "{\"a\": 1, \"b\": 2}");
    EXPECT_EQ(result, "Error: invalid input");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}