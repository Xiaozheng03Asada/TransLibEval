#include <gtest/gtest.h>
#include "../src/function_autopep8_format_code_with_custom_style.cpp"

class TestAutoPep8CustomStyle : public ::testing::Test {
protected:
    CodeFormatter formatter;
};

TEST_F(TestAutoPep8CustomStyle, TestFormatCodeBasic) {
    std::string code = "def bad_function(x,y):print(x+y)";
    std::string expected_code = "def bad_function (x, y) : print (x + y)";
    std::string formatted_code = formatter.format_code_with_custom_style(code);
    EXPECT_EQ(formatted_code, expected_code);
}

TEST_F(TestAutoPep8CustomStyle, TestFormatCodeMultipleArguments) {
    std::string code = "def multi_param_function(a,b,c,d):return a+b+c+d";
    std::string expected_code = "def multi_param_function (a, b, c, d) : return a + b + c + d";
    std::string formatted_code = formatter.format_code_with_custom_style(code);
    EXPECT_EQ(formatted_code, expected_code);
}

TEST_F(TestAutoPep8CustomStyle, TestFormatCodeMultiline) {
    std::string code = "def long_function():\n    a=1\n    b=2\n    return a+b";
    std::string expected_code = "def long_function () : a = 1 b = 2 return a + b";
    std::string formatted_code = formatter.format_code_with_custom_style(code);
    EXPECT_EQ(formatted_code, expected_code);
}

TEST_F(TestAutoPep8CustomStyle, TestFormatCodeAlreadyCorrect) {
    std::string code = "def already_correct_function(a, b): return a + b";
    std::string expected_code = "def already_correct_function (a, b) : return a + b";
    std::string formatted_code = formatter.format_code_with_custom_style(code);
    EXPECT_EQ(formatted_code, expected_code);
}

TEST_F(TestAutoPep8CustomStyle, TestFormatCodeIndentation) {
    std::string code = "def function():\na=1\n    b=2\n    return a+b";
    std::string expected_code = "def function () : a = 1 b = 2 return a + b";
    std::string formatted_code = formatter.format_code_with_custom_style(code);
    EXPECT_EQ(formatted_code, expected_code);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}