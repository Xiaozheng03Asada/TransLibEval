import unittest
from function_algorithms_heap_heapify import HeapifyFunction


class TestHeapify(unittest.TestCase):
    def setUp(self):
        self.function = HeapifyFunction()

    def test_standard_case(self):
        arr_str = "5,3,8,4,2,7,1,6"
        heap = self.function.heapify_from_string(arr_str)

        # Convert the output string back to a list to validate heap property
        heap_list = list(map(int, heap.split(',')))

        # Check that the heap's root is the smallest number
        self.assertEqual(heap_list[0], 1)

        for i in range(len(heap_list)):
            left = 2 * i + 1
            right = 2 * i + 2
            if left < len(heap_list):
                self.assertLessEqual(heap_list[i], heap_list[left])  # Ensure the heap property holds
            if right < len(heap_list):
                self.assertLessEqual(heap_list[i], heap_list[right])  # Ensure the heap property holds

    def test_single_element(self):
        arr_str = "10"
        self.assertEqual(self.function.heapify_from_string(arr_str), "10")

    def test_sorted_array(self):
        arr_str = "1,2,3,4,5"
        self.assertEqual(self.function.heapify_from_string(arr_str), "1,2,3,4,5")

    def test_all_identical_elements(self):
        arr_str = "7,7,7,7,7"
        self.assertEqual(self.function.heapify_from_string(arr_str), "7,7,7,7,7")

    def test_large_numbers_with_negatives(self):
        arr_str = "1000000,-5000000,3000000,-2000000,4000000"
        expected_result = "-5000000,-2000000,3000000,1000000,4000000"  # Min-heap
        self.assertEqual(self.function.heapify_from_string(arr_str), expected_result)


if __name__ == "__main__":
    unittest.main()
