#include <gtest/gtest.h>
#include "../src/function_boltons_cacheutils_LRU.cpp"

class LRUCacheManagerTest : public ::testing::Test {
protected:
    LRUCacheManager function;
};

TEST_F(LRUCacheManagerTest, TestBasicSetAndGet) {
    std::string operations_str = "set,a,1,set,b,2,get,a,get,b";
    std::string result = function.manage_cache_operations(3, operations_str);
    EXPECT_EQ(result, "1,2");
}

TEST_F(LRUCacheManagerTest, TestLRUEviction) {
    std::string operations_str = "set,a,1,set,b,2,set,c,3,set,d,4,get,a,get,b";
    std::string result = function.manage_cache_operations(3, operations_str);
    EXPECT_EQ(result, "None,2");
}

TEST_F(LRUCacheManagerTest, TestUpdateExistingKey) {
    std::string operations_str = "set,a,1,set,a,10,get,a";
    std::string result = function.manage_cache_operations(2, operations_str);
    EXPECT_EQ(result, "10");
}

TEST_F(LRUCacheManagerTest, TestCacheSizeLimit) {
    std::string operations_str = "set,a,1,set,b,2,set,c,3,set,d,4,get,a,get,b,get,c,get,d";
    std::string result = function.manage_cache_operations(2, operations_str);
    EXPECT_EQ(result, "None,None,3,4");
}

TEST_F(LRUCacheManagerTest, TestEmptyCacheOperations) {
    std::string operations_str = "";
    std::string result = function.manage_cache_operations(2, operations_str);
    EXPECT_EQ(result, "");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}