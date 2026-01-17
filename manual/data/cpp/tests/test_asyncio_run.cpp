#include <gtest/gtest.h>
#include "../src/function_asyncio_run.cpp"


class TestAsyncioRun : public ::testing::Test {
protected:
    AsyncTaskHandler handler;
};

TEST_F(TestAsyncioRun, TestAsyncioRunWithDelayedTask) {
    std::string result = handler.run_async_task("delayed", 2);
    EXPECT_EQ(result, "Task delayed completed after 2 seconds");
}

TEST_F(TestAsyncioRun, TestMultipleAsyncioRunTasks) {
    std::string result1 = handler.run_async_task("task1", 1);
    std::string result2 = handler.run_async_task("task2", 2);
    EXPECT_EQ(result1, "Task task1 completed after 1 seconds");
    EXPECT_EQ(result2, "Task task2 completed after 2 seconds");
}

TEST_F(TestAsyncioRun, TestAsyncioRunEmptyTask) {
    std::string result = handler.run_async_task("empty", 0);
    EXPECT_EQ(result, "Task empty completed after 0 seconds");
}

TEST_F(TestAsyncioRun, TestAsyncioRunInDifferentThread) {
    std::thread t([this]() {
        std::string result = handler.run_async_task("task_in_thread", 1);
        EXPECT_EQ(result, "Task task_in_thread completed after 1 seconds");
    });
    t.join();
}

TEST_F(TestAsyncioRun, TestAsyncioRunWithMultipleTasksInParallel) {
    std::string result1 = handler.run_async_task("task1_parallel", 1);
    std::string result2 = handler.run_async_task("task2_parallel", 2);
    EXPECT_EQ(result1, "Task task1_parallel completed after 1 seconds");
    EXPECT_EQ(result2, "Task task2_parallel completed after 2 seconds");
}

int main(int argc, char** argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}