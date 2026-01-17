import unittest
from function_tqdm_trange import ProgressRange

class TestTrangeFunction(unittest.TestCase):

    def test_type_of_result(self):
        result = ProgressRange.might_fail_function(0, 5, 1)
        self.assertEqual(type(result), str)

    def test_custom_miniters(self):
        result = ProgressRange.might_fail_function(0, 5, 1, miniters=2)
        self.assertEqual(len(result), 5)

    def test_custom_mininterval(self):
        result = ProgressRange.might_fail_function(0, 5, 1, mininterval=0.1)
        self.assertEqual(len(result), 5)

    def test_desc_updated(self):
        result = ProgressRange.might_fail_function(0, 5, 1, desc='New description')
        self.assertEqual(len(result), 5)

    def test_ascii_false(self):
        result = ProgressRange.might_fail_function(0, 5, 1, ascii=False)
        self.assertEqual(len(result), 5)

if __name__ == '__main__':
    unittest.main()
