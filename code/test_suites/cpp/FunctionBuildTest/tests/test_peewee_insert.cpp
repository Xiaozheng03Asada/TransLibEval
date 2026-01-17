#include <gtest/gtest.h>
#include "../src/function_peewee_insert.cpp"


class TestPeeweeInsert : public ::testing::Test {
protected:
    PeeweeInsert executor;
    std::string db_path = "test.db";

    void SetUp() override {
        if (std::filesystem::exists(db_path)) {
            std::filesystem::remove(db_path);
        }
    }

    void TearDown() override {
        if (std::filesystem::exists(db_path)) {
            std::filesystem::remove(db_path);
        }
    }
};

bool is_digit(const std::string& s) {
    return !s.empty() && std::all_of(s.begin(), s.end(), ::isdigit);
}

TEST_F(TestPeeweeInsert, test_valid_insert) {
    std::string result = executor.insert_record(db_path, "Alice", 25);
    EXPECT_TRUE(is_digit(result)); 
}

TEST_F(TestPeeweeInsert, test_empty_name) {
    std::string result = executor.insert_record(db_path, "", 30);
    EXPECT_TRUE(is_digit(result));  
}

TEST_F(TestPeeweeInsert, test_zero_age) {
    std::string result = executor.insert_record(db_path, "Bob", 0);
    EXPECT_TRUE(is_digit(result));  
}

TEST_F(TestPeeweeInsert, test_negative_age) {
    std::string result = executor.insert_record(db_path, "Charlie", -5);
    EXPECT_TRUE(is_digit(result)); 
}

TEST_F(TestPeeweeInsert, test_large_age) {
    std::string result = executor.insert_record(db_path, "Dave", 150);
    EXPECT_TRUE(is_digit(result)); 
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}