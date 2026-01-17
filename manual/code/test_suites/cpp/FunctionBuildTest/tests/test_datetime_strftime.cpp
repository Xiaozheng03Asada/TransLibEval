
#include <gtest/gtest.h>
#include "../src/function_datetime_strftime.cpp"

class TestFormatCurrentTime : public testing::Test {
protected:
    TimeFormatter time_formatter;
};

TEST_F(TestFormatCurrentTime, SpecificTime) {
    std::string expected_result = "2024-11-11 10:30:45";
    std::string result = time_formatter.format_current_time(2024, 11, 11, 10, 30, 45);
    EXPECT_EQ(result, expected_result);
}

TEST_F(TestFormatCurrentTime, DateOnly) {
    std::string expected_result = "2023-01-01 00:00:00";
    std::string result = time_formatter.format_current_time(2023, 1, 1, 0, 0, 0);
    EXPECT_EQ(result, expected_result);
}

TEST_F(TestFormatCurrentTime, MidnightTime) {
    std::string expected_result = "2024-01-01 00:00:00";
    std::string result = time_formatter.format_current_time(2024, 1, 1, 0, 0, 0);
    EXPECT_EQ(result, expected_result);
}

TEST_F(TestFormatCurrentTime, EndOfYear) {
    std::string expected_result = "2023-12-31 23:59:59";
    std::string result = time_formatter.format_current_time(2023, 12, 31, 23, 59, 59);
    EXPECT_EQ(result, expected_result);
}

TEST_F(TestFormatCurrentTime, LeapYear) {
    std::string expected_result = "2024-02-29 12:00:00";
    std::string result = time_formatter.format_current_time(2024, 2, 29, 12, 0, 0);
    EXPECT_EQ(result, expected_result);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}