#include <gtest/gtest.h>
#include <string>
#include "../src/function_TextBlob_polarity.cpp"

class TestTextProcessor : public ::testing::Test {
protected:
    TextProcessor processor;
};

TEST_F(TestTextProcessor, test_positive_sentiment) {
    std::string result = processor.analyze_sentiment<std::string>("I love programming!");
    EXPECT_EQ(result, "Positive");
}

TEST_F(TestTextProcessor, test_negative_sentiment) {
    std::string result = processor.analyze_sentiment<std::string>("I hate bugs in my code.");
    EXPECT_EQ(result, "Negative");
}

TEST_F(TestTextProcessor, test_neutral_sentiment) {
    std::string result = processor.analyze_sentiment<std::string>("This is a table.");
    EXPECT_EQ(result, "Neutral");
}

TEST_F(TestTextProcessor, test_empty_string) {
    std::string result = processor.analyze_sentiment<std::string>("");
    EXPECT_EQ(result, "Neutral");
}

TEST_F(TestTextProcessor, test_non_text_input) {
    std::string result = processor.analyze_sentiment<std::string>("123456");
    EXPECT_EQ(result, "Neutral");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}