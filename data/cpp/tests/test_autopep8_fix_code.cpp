#include <gtest/gtest.h>
#include "../src/function_autopep8_fix_code.cpp"

class TestAutoPep8 : public ::testing::Test {
protected:
    CodeFormatter formatter;
};

TEST_F(TestAutoPep8, TestSimpleCode) {
    std::string code = "def example_function(x,y):print(x+y)";
    std::string formatted_code = formatter.format_code(code);
    std::string expected_code = "def example_function(x, y) : print(x + y)";
    EXPECT_EQ(formatted_code, expected_code);
}

TEST_F(TestAutoPep8, TestMultipleIssuesCode) {
    std::string code = "\n        def bad_function(a  , b):x=a+b\n        ";
    std::string formatted_code = formatter.format_code(code);
    std::string expected_code = "\ndef bad_function(a, b) : x = a + b\n";
    EXPECT_EQ(formatted_code, expected_code);
}

TEST_F(TestAutoPep8, TestAlreadyFormattedCode) {
    std::string code = "\ndef bad_function(a, b): x = a + b\n";
    std::string formatted_code = formatter.format_code(code);
    std::string expected_code = "\ndef bad_function(a, b) : x = a + b\n";
    EXPECT_EQ(formatted_code, expected_code);
}

TEST_F(TestAutoPep8, TestInvalidCode) {
    std::string code = "{\"name\": \"Alice\", \"age\": 30}";
    std::string formatted_code = formatter.format_code(code);
    EXPECT_EQ(formatted_code, "{ \"name\" : \"Alice\", \"age\" : 30 }");
}

TEST_F(TestAutoPep8, TestSyntaxErrorCode) {
    std::string code = "def example_function(x, y):\n    return x + y(";
    try {
        std::string formatted_code = formatter.format_code(code);
        EXPECT_NE(formatted_code.find("def example_function(x, y):"), std::string::npos);
    } catch (const std::exception& e) {
        FAIL() << "autopep8 failed with error: " << e.what();
    }
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}