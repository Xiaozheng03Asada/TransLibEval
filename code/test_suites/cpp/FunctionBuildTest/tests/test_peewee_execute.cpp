#include <gtest/gtest.h>
#include "../src/function_peewee_execute.cpp"



class TestPeeweeExecute : public ::testing::Test {
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

TEST_F(TestPeeweeExecute, test_create_table) {
    std::string result = executor.execute_query(db_path, "CREATE TABLE IF NOT EXISTS test (name TEXT);");
    EXPECT_EQ(result, "-1");
}

TEST_F(TestPeeweeExecute, test_insert_data) {
    std::string result = executor.execute_query(db_path, "INSERT INTO test (name) VALUES ('Alice');");
    EXPECT_EQ(result, "1");
}

TEST_F(TestPeeweeExecute, test_multiple_inserts) {
    std::string result = executor.execute_query(db_path, "INSERT INTO test (name) VALUES ('Bob'), ('Charlie');");
    EXPECT_EQ(result, "2");
}

TEST_F(TestPeeweeExecute, test_update_data) {
    executor.execute_query(db_path, "INSERT INTO test (name) VALUES ('David');");
    std::string result = executor.execute_query(db_path, "UPDATE test SET name = 'Dave' WHERE name = 'David';");
    EXPECT_EQ(result, "1");
}

TEST_F(TestPeeweeExecute, test_delete_data) {
    executor.execute_query(db_path, "INSERT INTO test (name) VALUES ('Eve');");
    std::string result = executor.execute_query(db_path, "DELETE FROM test WHERE name = 'Eve';");
    EXPECT_EQ(result, "1");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}