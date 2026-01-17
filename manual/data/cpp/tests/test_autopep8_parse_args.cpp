#include <gtest/gtest.h>
#include "../src/function_autopep8_parse_args.cpp"

class TestArgumentParser : public ::testing::Test {
protected:
    ArgumentParser parser;
};

TEST_F(TestArgumentParser, ParseArgsBasic) {
    std::string input_str = "-n Alice -a 30";
    std::string result = parser.parse_arguments(input_str);
    EXPECT_EQ(result, "Name: Alice, Age: 30, City: Not provided");
}

TEST_F(TestArgumentParser, ParseArgsWithCity) {
    std::string input_str = "-n Bob -a 25 -c New York";
    std::string result = parser.parse_arguments(input_str);
    EXPECT_EQ(result, "Error: 2");
}

TEST_F(TestArgumentParser, ParseArgsWithoutCity) {
    std::string input_str = "-n Charlie -a 40";
    std::string result = parser.parse_arguments(input_str);
    EXPECT_EQ(result, "Name: Charlie, Age: 40, City: Not provided");
}

TEST_F(TestArgumentParser, ParseArgsMissingAge) {
    std::string input_str = "-n Dave";
    std::string result = parser.parse_arguments(input_str);
    EXPECT_TRUE(result.find("Error:") != std::string::npos);
}

TEST_F(TestArgumentParser, ParseArgsMissingName) {
    std::string input_str = "-a 50";
    std::string result = parser.parse_arguments(input_str);
    EXPECT_TRUE(result.find("Error:") != std::string::npos);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}