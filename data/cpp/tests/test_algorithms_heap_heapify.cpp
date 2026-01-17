#include <gtest/gtest.h>
#include "../src/function_algorithms_heap_heapify.cpp"

class TestHeapify : public ::testing::Test {
protected:
    HeapifyFunction function;
};

TEST_F(TestHeapify, TestStandardCase) {
    std::string arr_str = "5,3,8,4,2,7,1,6";
    std::string result = function.heapify_from_string(arr_str);
    
    // 验证结果是否为最小堆
    std::vector<int> heap;
    std::stringstream ss(result);
    std::string item;
    while (std::getline(ss, item, ',')) {
        heap.push_back(std::stoi(item));
    }
    
    // 检查根节点是否为最小值
    EXPECT_EQ(heap[0], 1);
    
    // 验证堆的性质
    for (size_t i = 0; i < heap.size(); i++) {
        size_t left = 2 * i + 1;
        size_t right = 2 * i + 2;
        if (left < heap.size())
            EXPECT_LE(heap[i], heap[left]);
        if (right < heap.size())
            EXPECT_LE(heap[i], heap[right]);
    }
}

TEST_F(TestHeapify, TestSingleElement) {
    EXPECT_EQ(function.heapify_from_string("10"), "10");
}

TEST_F(TestHeapify, TestSortedArray) {
    EXPECT_EQ(function.heapify_from_string("1,2,3,4,5"), "1,2,3,4,5");
}

TEST_F(TestHeapify, TestAllIdenticalElements) {
    EXPECT_EQ(function.heapify_from_string("7,7,7,7,7"), "7,7,7,7,7");
}

TEST_F(TestHeapify, TestLargeNumbersWithNegatives) {
    std::string input = "1000000,-5000000,3000000,-2000000,4000000";
    std::string expected = "-5000000,-2000000,3000000,1000000,4000000";
    EXPECT_EQ(function.heapify_from_string(input), expected);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}