#include <gtest/gtest.h>
#include "../src/function_pycrypto_sha256_hasher.cpp"

class TestSHA256Hasher : public ::testing::Test {
protected:
    void SetUp() override {
        hasher = new SHA256Hasher();
    }

    void TearDown() override {
        delete hasher;
    }
    
    SHA256Hasher* hasher;
};

TEST_F(TestSHA256Hasher, SpecialCharactersHash) {
    std::string special_text = "This is a test! ";
    std::string result = hasher->hash_text(special_text);
    std::string expected_hash = hasher->hash_text(special_text);
    EXPECT_EQ(result, expected_hash);
}

TEST_F(TestSHA256Hasher, HashIsUniqueForDifferentInputs) {
    std::string text1 = "Test input one";
    std::string text2 = "Test input two";
    std::string hash1 = hasher->hash_text(text1);
    std::string hash2 = hasher->hash_text(text2);
    EXPECT_NE(hash1, hash2);
}

TEST_F(TestSHA256Hasher, EmptyStringHash) {
    std::string result = hasher->hash_text("");
    std::string expected_hash = hasher->hash_text("");
    EXPECT_EQ(result, expected_hash);
}

TEST_F(TestSHA256Hasher, CollisionResistance) {
    std::string input1 = "Hello, world!";
    std::string input2 = "Hello, world!!";
    std::string hash1 = hasher->hash_text(input1);
    std::string hash2 = hasher->hash_text(input2);
    EXPECT_NE(hash1, hash2);
}

TEST_F(TestSHA256Hasher, LengthInvariance) {
    std::string small_input = "Short text";
    std::string large_input(1000, 'A');  // 1000个'A'字符
    std::string hash_small = hasher->hash_text(small_input);
    std::string hash_large = hasher->hash_text(large_input);
    EXPECT_EQ(hash_small.length(), 64);  // SHA-256哈希的十六进制表示为64个字符
    EXPECT_EQ(hash_large.length(), 64);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}