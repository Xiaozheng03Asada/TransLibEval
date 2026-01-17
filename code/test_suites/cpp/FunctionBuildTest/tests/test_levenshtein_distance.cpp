#include "gtest/gtest.h"
#include "../src/function_levenshtein_distance.cpp"

TEST(LevenshteinDistanceTest, EqualStrings) {
    StringProcessor processor;
    EXPECT_EQ(processor.calculate_levenshtein_distance(std::string("test"), std::string("test")), 0);
}

TEST(LevenshteinDistanceTest, InsertionsAndDeletions) {
    StringProcessor processor;
    EXPECT_EQ(processor.calculate_levenshtein_distance(std::string("hello"), std::string("helo")), 1);
    EXPECT_EQ(processor.calculate_levenshtein_distance(std::string("helo"), std::string("hello")), 1);
}

TEST(LevenshteinDistanceTest, SingleSubstitution) {
    StringProcessor processor;
    EXPECT_EQ(processor.calculate_levenshtein_distance(std::string("flaw"), std::string("lawn")), 2);
}

TEST(LevenshteinDistanceTest, MultipleOperations) {
    StringProcessor processor;
    EXPECT_EQ(processor.calculate_levenshtein_distance(std::string("kitten"), std::string("sitting")), 3);
}

TEST(LevenshteinDistanceTest, NonStringInput) {
    StringProcessor processor;
    EXPECT_THROW(processor.calculate_levenshtein_distance(123, std::string("hello")), std::invalid_argument);
    EXPECT_THROW(processor.calculate_levenshtein_distance(std::string("hello"), nullptr), std::invalid_argument);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}