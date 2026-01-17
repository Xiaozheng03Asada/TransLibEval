import unittest
from function_mlxtend_fit import OneHotEncode

class TestOneHotEncode(unittest.TestCase):

    def test_single_transaction(self):
        onehot_instance = OneHotEncode()
        self.assertEqual(onehot_instance.onehot_encode('milk bread butter'), 'bread, butter, milk')

    def test_multiple_transactions(self):
        onehot_instance = OneHotEncode()
        self.assertEqual(onehot_instance.onehot_encode('milk bread, bread butter'), 'bread, butter, milk')

    def test_empty_transaction(self):
        onehot_instance = OneHotEncode()
        self.assertEqual(onehot_instance.onehot_encode(''), '')

    def test_repeated_items(self):
        onehot_instance = OneHotEncode()
        self.assertEqual(onehot_instance.onehot_encode('milk bread milk'), 'bread, milk')

    def test_non_empty_unique_items(self):
        onehot_instance = OneHotEncode()
        self.assertEqual(onehot_instance.onehot_encode('milk butter cheese'), 'butter, cheese, milk')

if __name__ == '__main__':
    unittest.main()
