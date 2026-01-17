#include <gtest/gtest.h>
#include "../src/function_datetime_timedelta.cpp"

class TestDateTimeOperations : public testing::Test {
protected:
    DateTimeModifier datetime_modifier;
};

TEST_F(TestDateTimeOperations, AddDays) {
    std::string result = datetime_modifier.get_modified_datetime("2024-11-11 10:30:45", 5);
    EXPECT_EQ(result, "2024-11-16 10:30:45");
}

TEST_F(TestDateTimeOperations, SubtractDays) {
    std::string result = datetime_modifier.get_modified_datetime("2024-11-11 10:30:45", -3);
    EXPECT_EQ(result, "2024-11-08 10:30:45");
}

TEST_F(TestDateTimeOperations, AddHours) {
    std::string result = datetime_modifier.get_modified_datetime("2024-11-11 10:30:45", 0, 5);
    EXPECT_EQ(result, "2024-11-11 15:30:45");
}

TEST_F(TestDateTimeOperations, SubtractHours) {
    std::string result = datetime_modifier.get_modified_datetime("2024-11-11 10:30:45", 0, -5);
    EXPECT_EQ(result, "2024-11-11 05:30:45");
}

TEST_F(TestDateTimeOperations, AddWeeks) {
    std::string result = datetime_modifier.get_modified_datetime("2024-11-11 10:30:45", 0, 0, 2);
    EXPECT_EQ(result, "2024-11-25 10:30:45");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}