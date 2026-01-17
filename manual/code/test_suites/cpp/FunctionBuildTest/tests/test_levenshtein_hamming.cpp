#include <gtest/gtest.h>
#include "../src/function_levenshtein_hamming.cpp"

class TestHammingDistance : public ::testing::Test {
protected:
    void SetUp() override {
        processor = new StringProcessor();
    }
    void TearDown() override {
        delete processor;
    }
    StringProcessor* processor;
};

TEST_F(TestHammingDistance, EqualStrings) {
    std::string result = processor->calculate_hamming_distance("test", "test");
    EXPECT_EQ(result, "0");
}

TEST_F(TestHammingDistance, SingleSubstitution) {
    std::string result = processor->calculate_hamming_distance("flaw", "lawn");
    EXPECT_EQ(result, "4");
}

TEST_F(TestHammingDistance, SingleCharacterDifference) {
    std::string result = processor->calculate_hamming_distance("objective", "objection");
    EXPECT_EQ(result, "2");
}

TEST_F(TestHammingDistance, DifferentLengths) {
    std::string result = processor->calculate_hamming_distance("hello", "helo");
    EXPECT_EQ(result, "Strings must be of the same length for Hamming distance.");
}

TEST_F(TestHammingDistance, NonStringInput) {
    std::string result = processor->calculate_hamming_distance(123, "hello");
    EXPECT_EQ(result, "Error: Both inputs must be strings");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}