
#include <gtest/gtest.h>
#include "../src/function_datetime_now.cpp"


class TestDateTimeAPI : public testing::Test {
protected:
    DateTimeModifier datetime_modifier;
};

TEST_F(TestDateTimeAPI, CurrentDatetimeInput) {
    std::string date_string = "2024-11-11 10:30:45";
    std::string result = datetime_modifier.get_current_datetime(date_string);
    EXPECT_EQ(result, "2024-11-11 10:30:45");
}

TEST_F(TestDateTimeAPI, CurrentDatetimeDefault) {
    std::string result = datetime_modifier.get_current_datetime();
    EXPECT_EQ(result.length(), 19);
    std::regex datetime_pattern(R"(\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2})");
    EXPECT_TRUE(std::regex_match(result, datetime_pattern));
}

TEST_F(TestDateTimeAPI, CurrentDatetimeInvalidFormat) {
    std::string date_string = "2024-11-11 10:30";
    EXPECT_THROW({
        datetime_modifier.get_current_datetime(date_string);
    }, std::invalid_argument);
}

TEST_F(TestDateTimeAPI, CurrentDatetimeChanges) {
    std::string date_string = "2024-11-11 10:30:45";
    std::string result1 = datetime_modifier.get_current_datetime(date_string);
    std::string result2 = datetime_modifier.get_current_datetime(date_string);
    EXPECT_EQ(result1, result2);
}

TEST_F(TestDateTimeAPI, CurrentDatetimeEmptyString) {
    std::string date_string = "";
    std::string result = datetime_modifier.get_current_datetime(date_string);
    EXPECT_EQ(result, "1900-01-01 00:00:00");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}