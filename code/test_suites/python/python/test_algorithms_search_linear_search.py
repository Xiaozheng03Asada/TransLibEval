import unittest
from function_algorithms_search_linear_search import LinearSearchFunction

class TestLinearSearchFunction(unittest.TestCase):
    def setUp(self):
        self.function = LinearSearchFunction()

    def test_element_found(self):
        arr_str = "10,20,30,40,50"
        target_str = 30
        self.assertEqual(self.function.linear_search_from_string(arr_str, target_str), 2)

    def test_element_not_found(self):
        arr_str = "10,20,30,40,50"
        target_str = 60
        self.assertEqual(self.function.linear_search_from_string(arr_str, target_str), -1)

    def test_empty_array(self):
        arr_str = ""
        target_str = 30
        self.assertEqual(self.function.linear_search_from_string(arr_str, target_str), -1)

    def test_first_element(self):
        arr_str = "10,20,30,40,50"
        target_str = 10
        self.assertEqual(self.function.linear_search_from_string(arr_str, target_str), 0)

    def test_last_element(self):
        arr_str = "10,20,30,40,50"
        target_str = 50
        self.assertEqual(self.function.linear_search_from_string(arr_str, target_str), 4)

if __name__ == "__main__":
    unittest.main()
