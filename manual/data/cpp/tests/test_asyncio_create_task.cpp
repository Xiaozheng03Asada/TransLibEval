#include <gtest/gtest.h>
#include "../src/function_asyncio_create_task.cpp"

class TestAsyncTaskHandler : public ::testing::Test {
protected:
    AsyncTaskHandler handler;
};

TEST_F(TestAsyncTaskHandler, TestTaskWithDelay) {
    std::string result = handler.run_task("delayed", 2);
    EXPECT_EQ(result, "Task delayed completed after 2 seconds");
}

TEST_F(TestAsyncTaskHandler, TestMultipleTasks) {
    std::string result1 = handler.run_task("task1", 1);
    std::string result2 = handler.run_task("task2", 2);
    EXPECT_EQ(result1, "Task task1 completed after 1 seconds");
    EXPECT_EQ(result2, "Task task2 completed after 2 seconds");
}

TEST_F(TestAsyncTaskHandler, TestTaskWithNoDelay) {
    std::string result = handler.run_task("immediate", 0);
    EXPECT_EQ(result, "Task immediate completed after 0 seconds");
}
TEST_F(TestAsyncTaskHandler, TestTaskWithLongDelay) {
    std::string result = handler.run_task("long_delay", 5);
    EXPECT_EQ(result, "Task long_delay completed after 5 seconds");
}
TEST_F(TestAsyncTaskHandler, TestTaskWithInvalidDelay) {
    EXPECT_THROW(handler.run_task("invalid", -1), std::invalid_argument);
}

int main(int argc, char** argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}