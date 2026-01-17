#include <gtest/gtest.h>
#include <string>
#include "../src/function_TextBlob_spellcheck.cpp"

class TestSpellChecker : public ::testing::Test {
protected:
    SpellChecker checker;
};

TEST_F(TestSpellChecker, test_correct_spelling) {
    std::string result = checker.correct_spelling<std::string>("I lov Python");
    EXPECT_EQ(result, "I love Python");
}

TEST_F(TestSpellChecker, test_mixed_correct_and_incorrect_words) {
    std::string result = checker.correct_spelling<std::string>("I am hapy with this reslt");
    EXPECT_EQ(result, "I am happy with this result");
}

TEST_F(TestSpellChecker, test_empty_input) {
    std::string result = checker.correct_spelling<std::string>("");
    EXPECT_EQ(result, "");
}

TEST_F(TestSpellChecker, test_already_correct) {
    std::string result = checker.correct_spelling<std::string>("Hello world");
    EXPECT_EQ(result, "Hello world");
}

TEST_F(TestSpellChecker, test_return_type) {
    std::string input = "Gud morning";
    auto result = checker.correct_spelling<std::string>(input);
    EXPECT_TRUE((std::is_same_v<decltype(result), std::string>));
}
int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}