#include <gtest/gtest.h>
#include "../src/function_collections_defaultdict.cpp"

class TestWordCounter : public testing::Test {
protected:
    WordCounter counter;
};

TEST_F(TestWordCounter, NormalCase) {
    std::string word_string = "apple,banana,apple,cherry,banana,apple";
    std::string result = counter.count_words(word_string);
    EXPECT_EQ(result, "apple:3;banana:2;cherry:1");
}

TEST_F(TestWordCounter, EmptyString) {
    std::string word_string = "";
    std::string result = counter.count_words(word_string);
    EXPECT_EQ(result, "");
}

TEST_F(TestWordCounter, NonStringElements) {
    std::string word_string = "apple,123,banana,apple,true,cherry,banana,apple";
    std::string result = counter.count_words(word_string);
    EXPECT_EQ(result, "apple:3;123:1;banana:2;true:1;cherry:1");
}

TEST_F(TestWordCounter, CaseSensitivity) {
    std::string word_string = "Apple,apple,Banana,banana";
    std::string result = counter.count_words(word_string);
    EXPECT_EQ(result, "apple:2;banana:2");
}

TEST_F(TestWordCounter, ErrorHandling) {
    EXPECT_THROW({
        counter.count_words(123);
    }, std::invalid_argument);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}