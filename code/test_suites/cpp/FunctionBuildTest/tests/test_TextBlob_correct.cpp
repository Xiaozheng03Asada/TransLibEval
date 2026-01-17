#include <gtest/gtest.h>
#include "../src/function_TextBlob_correct.cpp"

class TestTextProcessor : public ::testing::Test {
protected:
    TextProcessor processor;
};

TEST_F(TestTextProcessor, test_correct_spelling_complex) {
    std::string result = processor.correct_spelling<std::string>("I relly enjy programing.");
    EXPECT_EQ(result, "I really enjoy programming.");
}

TEST_F(TestTextProcessor, test_correct_spelling_no_correction_needed) {
    std::string result = processor.correct_spelling<std::string>("Hello world!");
    EXPECT_EQ(result, "Hello world!");
}

TEST_F(TestTextProcessor, test_correct_spelling_empty_string) {
    std::string result = processor.correct_spelling<std::string>("");
    EXPECT_EQ(result, "");
}

TEST_F(TestTextProcessor, test_correct_spelling_numbers_and_symbols) {
    std::string result = processor.correct_spelling<std::string>("1234 @#$%^&*()");
    EXPECT_EQ(result, "1234 @#$%^&*()");
}

TEST_F(TestTextProcessor, test_correct_spelling_long_text) {
    std::string text = "Ths is an exmple of a lng sentnce with severl speling mistakes. It shuld be correced propery.";
    std::string expected_output = "This is an example of a long sentence with several spelling mistakes. It should be corrected properly.";
    std::string result = processor.correct_spelling<std::string>(text);
    EXPECT_EQ(result, expected_output);
}



int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}