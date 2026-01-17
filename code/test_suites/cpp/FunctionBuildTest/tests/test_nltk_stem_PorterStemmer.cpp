#include <gtest/gtest.h>
#include "../src/function_nltk_stem_PorterStemmer.cpp"

class TestPorterStemmer : public testing::Test
{
protected:
    Stemmer stemmer;
};
TEST(TestPorterStemmer, TestStemRunning)
{
    Stemmer stemmer;
    std::string result = stemmer.test_stem("running");
    EXPECT_EQ(result, "run");
}

TEST(TestPorterStemmer, TestStemPlayed)
{
    Stemmer stemmer;
    std::string result = stemmer.test_stem("played");
    EXPECT_EQ(result, "play");
}

TEST(TestPorterStemmer, TestStemDogs)
{
    Stemmer stemmer;
    std::string result = stemmer.test_stem("dogs");
    EXPECT_EQ(result, "dog");
}

TEST(TestPorterStemmer, TestStemEmptyString)
{
    Stemmer stemmer;
    std::string result = stemmer.test_stem("");
    EXPECT_EQ(result, "");
}

TEST(TestPorterStemmer, TestStemWordWithSpecialCharacters)
{
    Stemmer stemmer;
    std::string result = stemmer.test_stem("$#@!word");
    EXPECT_EQ(result, "$#@!word");
}

int main(int argc, char **argv)
{
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}