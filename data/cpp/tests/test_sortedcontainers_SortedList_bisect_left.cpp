#include <gtest/gtest.h>
#include "../src/function_sortedcontainers_SortedList_bisect_left.cpp"

class TestSortedListBisectLeftMethod : public ::testing::Test {
protected:
    HanSeok handler;
};

TEST_F(TestSortedListBisectLeftMethod, test_value_in_middle) {
    int position = handler.find_insert_position(4);
    EXPECT_EQ(position, 2);
}

TEST_F(TestSortedListBisectLeftMethod, test_value_at_start) {
    int position = handler.find_insert_position(0);
    EXPECT_EQ(position, 0);
}

TEST_F(TestSortedListBisectLeftMethod, test_value_at_end) {
    int position = handler.find_insert_position(10);
    EXPECT_EQ(position, 4);
}

TEST_F(TestSortedListBisectLeftMethod, test_existing_value) {
    int position = handler.find_insert_position(3);
    EXPECT_EQ(position, 1);
}

TEST_F(TestSortedListBisectLeftMethod, test_insert_into_empty_list) {
    int position = handler.find_insert_position(5, "5");
    EXPECT_EQ(position, 0);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}