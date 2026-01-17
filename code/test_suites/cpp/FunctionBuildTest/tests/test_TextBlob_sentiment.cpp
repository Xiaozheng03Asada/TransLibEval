#include <gtest/gtest.h>
#include <string>
#include "../src/function_TextBlob_sentiment.cpp"

class TestTextProcessor : public ::testing::Test {
protected:
    TextProcessor processor;
};
 
TEST_F(TestTextProcessor, test_positive_sentiment) {
    std::string result = processor.analyze_sentiment<std::string>("I love this beautiful day!");
    EXPECT_EQ(result, "Positive");
}

TEST_F(TestTextProcessor, test_negative_sentiment) {
    std::string result = processor.analyze_sentiment<std::string>("I hate this terrible weather.");
    EXPECT_EQ(result, "Negative");
}

TEST_F(TestTextProcessor, test_neutral_sentiment) {
    std::string result = processor.analyze_sentiment<std::string>("The sky is blue.");
    EXPECT_EQ(result, "Neutral");
}

TEST_F(TestTextProcessor, test_empty_string) {
    std::string result = processor.analyze_sentiment<std::string>("");
    EXPECT_EQ(result, "Neutral");
}

TEST_F(TestTextProcessor, test_simple_positive) {
    std::string result = processor.analyze_sentiment<std::string>("Good job!");
    EXPECT_EQ(result, "Positive");
}


int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}