#include <gtest/gtest.h>
#include "../src/function_joblib_hashing_hash.cpp"

class TestHashingExample : public testing::Test {
protected:
    HashingExample calc;
};

TEST_F(TestHashingExample, ComputeHashString) {
    std::string result = calc.compute_hash("apple");
    EXPECT_FALSE(result.empty());
}

TEST_F(TestHashingExample, ComputeHashInteger) {
    std::string result = calc.compute_hash(123);
    EXPECT_FALSE(result.empty());
}

TEST_F(TestHashingExample, EmptyInput) {
    std::string result = calc.compute_hash("");
    EXPECT_FALSE(result.empty());
}

TEST_F(TestHashingExample, NonStringInput) {
    std::string result = calc.compute_hash(456.789);
    EXPECT_FALSE(result.empty());
}

TEST_F(TestHashingExample, EdgeCase) {
    std::string result = calc.compute_hash("apple apple apple");
    EXPECT_FALSE(result.empty());
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}