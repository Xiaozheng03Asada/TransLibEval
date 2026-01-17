import unittest
from function_collections_deque import DequeOperations

class TestDequeOperations(unittest.TestCase):

    def setUp(self):
        self.deque_operations = DequeOperations()

    def test_append_and_appendleft(self):
        result = self.deque_operations.perform_operation('append_and_appendleft')
        self.assertEqual(result, "0, 1")  # Check the first two elements

    def test_pop_and_popleft(self):
        result = self.deque_operations.perform_operation('pop_and_popleft')
        self.assertEqual(result, "1")  # Only one element left, length is 1


    def test_remove(self):
        result = self.deque_operations.perform_operation('remove')
        self.assertEqual(result, "1, 3")  # After removing 2, check the remaining elements

    def test_clear(self):
        result = self.deque_operations.perform_operation('clear')
        self.assertEqual(result, "0")  # After clearing, length is 0

    def test_invalid_operation(self):
        with self.assertRaises(ValueError):
            self.deque_operations.perform_operation('invalid_operation')

if __name__ == '__main__':
    unittest.main()