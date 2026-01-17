#include <gtest/gtest.h>
#include "../src/function_autopep8_fix_file.cpp"

class TestClangFormatter : public ::testing::Test {
protected:
    ClangFormatter formatter;
};

TEST_F(TestClangFormatter, FormatCodeBasic) {
    std::string code = "def bad_function(x,y):print(x+y)\n";
    std::string expected = "def bad_function(x, y) : print(x + y)\n";
    EXPECT_EQ(formatter.format_code(code), expected);
}

TEST_F(TestClangFormatter, FormatCodeWithEmptyLines) {
    std::string code = "\ndef bad_function(x,y):print(x+y)\n\ndef another_function(a,b):print(a+b)\n";
    std::string expected = "\ndef bad_function(x, y)\n    : print(x + y)\n\n          def another_function(a, b)\n    : print(a + b)\n";
    EXPECT_EQ(formatter.format_code(code), expected);
}

TEST_F(TestClangFormatter, FormatCodeMixedIndentation) {
    std::string code = "\ndef bad_function(x, y):\n\tprint(x+y)\n";
    std::string expected = "\ndef bad_function(x, y) : print(x + y)\n";
    EXPECT_EQ(formatter.format_code(code), expected);
}

TEST_F(TestClangFormatter, FormatCodeNoFunction) {
    std::string code = "\nx = 10\ny = 20\nz = x + y\nprint(z)\n";
    std::string expected = "\nx = 10 y = 20 z = x + y print(z)\n";
    EXPECT_EQ(formatter.format_code(code), expected);
}

TEST_F(TestClangFormatter, FormatCodeSingleLine) {
    std::string code = "x=1;y=2;z=x+y;print(z)\n";
    std::string expected = "x = 1;\ny = 2;\nz = x + y;\nprint(z)\n";
    EXPECT_EQ(formatter.format_code(code), expected);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}