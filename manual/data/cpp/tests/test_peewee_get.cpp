#include <gtest/gtest.h>
#include "../src/function_peewee_get.cpp"


class TestPeeweeExecutor : public ::testing::Test {
protected:
    PeeweeExecutor executor;
    std::string db_path = "test_db.sqlite";

    void SetUp() override {
    }

    void TearDown() override {
    }
};

TEST_F(TestPeeweeExecutor, test_existing_record) {
    EXPECT_EQ(executor.get_name_by_id(db_path, 1), "Alice");
}

TEST_F(TestPeeweeExecutor, test_existing_record_2) {
    EXPECT_EQ(executor.get_name_by_id(db_path, 2), "Bob");
}

TEST_F(TestPeeweeExecutor, test_nonexistent_record) {
    EXPECT_EQ(executor.get_name_by_id(db_path, 99), "Not Found");
}

TEST_F(TestPeeweeExecutor, test_zero_id) {
    EXPECT_EQ(executor.get_name_by_id(db_path, 0), "Not Found");
}

TEST_F(TestPeeweeExecutor, test_negative_id) {
    EXPECT_EQ(executor.get_name_by_id(db_path, -1), "Not Found");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}