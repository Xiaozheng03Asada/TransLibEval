#include <gtest/gtest.h>
#include "../src/function_asyncio_event.cpp"

class TestEventTaskHandler : public ::testing::Test {
protected:
    EventTaskHandler handler;
};

TEST_F(TestEventTaskHandler, TestTaskWhenEventTriggered) {
    std::string result = handler.might_fail_function("triggered", "A");
    EXPECT_EQ(result, "Task A completed");
}

TEST_F(TestEventTaskHandler, TestTaskWhenEventNotTriggered) {
    std::string result = handler.might_fail_function("waiting", "B");
    EXPECT_EQ(result, "Task B is waiting for the event");
}

TEST_F(TestEventTaskHandler, TestTaskWithEmptyEventStatus) {
    std::string result = handler.might_fail_function("", "C");
    EXPECT_EQ(result, "Task C is waiting for the event");
}

TEST_F(TestEventTaskHandler, TestTaskWithEmptyTaskName) {
    std::string result = handler.might_fail_function("triggered", "");
    EXPECT_EQ(result, "Task  completed");
}

TEST_F(TestEventTaskHandler, TestTaskWithNonTriggeredEventStatus) {
    std::string result = handler.might_fail_function("inactive", "D");
    EXPECT_EQ(result, "Task D is waiting for the event");
}

int main(int argc, char** argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}