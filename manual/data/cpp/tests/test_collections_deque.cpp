
#include <gtest/gtest.h>
#include "../src/function_collections_deque.cpp"

class TestDequeOperations : public testing::Test {
protected:
    DequeOperations deque_operations;
};

TEST_F(TestDequeOperations, AppendAndAppendLeft) {
    std::string result = deque_operations.perform_operation(std::string("append_and_appendleft"));
    EXPECT_EQ(result, "0, 1");
}

TEST_F(TestDequeOperations, PopAndPopLeft) {
    std::string result = deque_operations.perform_operation(std::string("pop_and_popleft"));
    EXPECT_EQ(result, "1");
}

TEST_F(TestDequeOperations, Remove) {
    std::string result = deque_operations.perform_operation(std::string("remove"));
    EXPECT_EQ(result, "1, 3");
}

TEST_F(TestDequeOperations, Clear) {
    std::string result = deque_operations.perform_operation(std::string("clear"));
    EXPECT_EQ(result, "0");
}

TEST_F(TestDequeOperations, InvalidOperation) {
    EXPECT_THROW({
        deque_operations.perform_operation(std::string("invalid_operation"));
    }, std::invalid_argument);
}


int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}