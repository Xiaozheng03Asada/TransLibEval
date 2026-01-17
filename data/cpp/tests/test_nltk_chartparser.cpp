#include "../src/function_nltk_chartparser.cpp"
#include <gtest/gtest.h>


TEST(TestChartParserTestCase, TestCorrectSentenceParsing) {
    std::string sentence = "the dog chased the cat";
    TestChartParser parser;
    auto result = parser.test_chartparser(sentence);
    EXPECT_GT(result.size(), 0);
}

TEST(TestChartParserTestCase, TestWrongWordOrder) {
    std::string sentence = "chased the dog the cat";
    TestChartParser parser;
    auto result = parser.test_chartparser(sentence);
    EXPECT_EQ(result.size(), 0);
}


TEST(TestChartParserTestCase, TestMissingWord) {
    std::string sentence = "the dog the cat";
    TestChartParser parser;
    auto result = parser.test_chartparser(sentence);
    EXPECT_EQ(result.size(), 0);
}


TEST(TestChartParserTestCase, TestDuplicateNouns) {
    std::string sentence = "the dog the dog chased the cat";
    TestChartParser parser;
    auto result = parser.test_chartparser(sentence);
    EXPECT_EQ(result.size(), 0);
}


TEST(TestChartParserTestCase, TestSingleWordSentence) {
    std::string sentence = "dog";
    TestChartParser parser;
    auto result = parser.test_chartparser(sentence);
    EXPECT_EQ(result.size(), 0);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}