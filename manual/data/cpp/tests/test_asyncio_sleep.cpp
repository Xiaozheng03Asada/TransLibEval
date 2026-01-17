#include <gtest/gtest.h>
#include "../src/function_asyncio_sleep.cpp"

class TestAsyncioSleep : public ::testing::Test {
protected:
    AsyncTaskHandler handler;
};

TEST_F(TestAsyncioSleep, TestAsyncioRunWithDelay) {
    std::string result = handler.run_async_task("Delayed", 2);
    EXPECT_EQ(result, "Task Delayed completed after 2.000000 seconds");
}

TEST_F(TestAsyncioSleep, TestMultipleAsyncioRunTasks) {
    std::string result1 = handler.run_async_task("Task1", 1);
    std::string result2 = handler.run_async_task("Task2", 2);
    EXPECT_EQ(result1, "Task Task1 completed after 1.000000 seconds");
    EXPECT_EQ(result2, "Task Task2 completed after 2.000000 seconds");
}

TEST_F(TestAsyncioSleep, TestAsyncioRunWithMultipleDelays) {
    std::string result1 = handler.run_async_task("Delayed 1", 2);
    std::string result2 = handler.run_async_task("Delayed 2", 3);
    EXPECT_EQ(result1, "Task Delayed 1 completed after 2.000000 seconds");
    EXPECT_EQ(result2, "Task Delayed 2 completed after 3.000000 seconds");
}

TEST_F(TestAsyncioSleep, TestAsyncioRunWithFloatDelay) {
    std::string result = handler.run_async_task("Float Delay", 0.5);
    EXPECT_EQ(result, "Task Float Delay completed after 0.500000 seconds");
}

TEST_F(TestAsyncioSleep, TestAsyncioRunWithCustomTask) {
    std::string result = handler.run_async_task("Custom Task", 1);
    EXPECT_EQ(result, "Task Custom Task completed after 1.000000 seconds");
}

int main(int argc, char** argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}