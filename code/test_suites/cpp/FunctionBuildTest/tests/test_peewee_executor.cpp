#include <gtest/gtest.h>
#include "../src/function_peewee_executor.cpp"


class TestPeeweeExecutor : public ::testing::Test {
protected:
    PeeweeExecutor executor;
    std::string db_path = "test_db.sqlite";

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

TEST_F(TestPeeweeExecutor, test_insert_query) {
    std::string result = executor.execute_query(db_path, "INSERT INTO testmodel (name) VALUES ('Alice')");
    EXPECT_EQ(result, "0");
}

TEST_F(TestPeeweeExecutor, test_select_query) {
    std::string result = executor.execute_query(db_path, "SELECT COUNT(*) FROM testmodel");
    EXPECT_TRUE(std::all_of(result.begin(), result.end(), ::isdigit));
}

TEST_F(TestPeeweeExecutor, test_update_query) {
    executor.execute_query(db_path, "INSERT INTO testmodel (name) VALUES ('Bob')");
    std::string result = executor.execute_query(db_path, "UPDATE testmodel SET name='Charlie' WHERE name='Bob'");
    EXPECT_EQ(result, "0");
}

TEST_F(TestPeeweeExecutor, test_delete_query) {
    executor.execute_query(db_path, "INSERT INTO testmodel (name) VALUES ('David')");
    std::string result = executor.execute_query(db_path, "DELETE FROM testmodel WHERE name='David'");
    EXPECT_EQ(result, "0");
}

TEST_F(TestPeeweeExecutor, test_invalid_query) {
    EXPECT_THROW(executor.execute_query(db_path, "INVALID SQL"), std::runtime_error);
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}