
#include <gtest/gtest.h>
#include "../src/function_functools_wraps.cpp"

class TestLRUCacheManager : public testing::Test {
protected:
    LRUCacheManager manager;
};

TEST_F(TestLRUCacheManager, BasicOperation) {
    std::string result = manager.manage_lru_cache("3", "set,a,1;set,b,2;get,a");
    EXPECT_EQ(result, "1");
}

TEST_F(TestLRUCacheManager, WithEviction) {
    std::string result = manager.manage_lru_cache("2", "set,a,1;set,b,2;set,c,3;get,a");
    EXPECT_EQ(result, "None");
}

TEST_F(TestLRUCacheManager, MultipleGets) {
    std::string result = manager.manage_lru_cache("2", "set,a,1;set,b,2;get,a;get,b");
    EXPECT_EQ(result, "1,2");
}

TEST_F(TestLRUCacheManager, EmptyOperations) {
    std::string result = manager.manage_lru_cache("3", "");
    EXPECT_EQ(result, "");
}

TEST_F(TestLRUCacheManager, NonExistentKey) {
    std::string result = manager.manage_lru_cache("3", "get,nonexistent_key");
    EXPECT_EQ(result, "None");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}