
#include <gtest/gtest.h>
#include "../src/function_datetime_strptime.cpp"

class TestDateParser : public testing::Test {
protected:
    DateParser parser;
};

TEST_F(TestDateParser, ValidDate) {
    std::string result = parser.parse_date("2023-03-25 14:30:00", "%Y-%m-%d %H:%M:%S");
    EXPECT_EQ(result, "2023-03-25 14:30:00");
}

TEST_F(TestDateParser, InvalidDateFormat) {
    std::string result = parser.parse_date("25/03/2023", "%Y-%m-%d %H:%M:%S");
    EXPECT_EQ(result, "failed");
}

TEST_F(TestDateParser, DifferentFormat) {
    std::string result = parser.parse_date("25/03/2023", "%d/%m/%Y");
    EXPECT_EQ(result, "2023-03-25 00:00:00");
}

TEST_F(TestDateParser, EmptyInput) {
    std::string result = parser.parse_date("", "%Y-%m-%d %H:%M:%S");
    EXPECT_EQ(result, "failed");
}

TEST_F(TestDateParser, NonDateInput) {
    std::string result = parser.parse_date("invalid-date", "%Y-%m-%d %H:%M:%S");
    EXPECT_EQ(result, "failed");
}