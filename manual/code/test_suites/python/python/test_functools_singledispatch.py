import unittest
from function_functools_singledispatch import DataProcessor

class TestProcessData(unittest.TestCase):

    def test_test_singledispatch_str(self):
        manager = DataProcessor()
        result = manager.test_singledispatch("hello")
        self.assertEqual(result, "HELLO")

    def test_test_singledispatch_int(self):
        manager = DataProcessor()
        result = manager.test_singledispatch(3)
        self.assertEqual(result, 6)

    def test_test_singledispatch_float(self):
        manager = DataProcessor()
        result = manager.test_singledispatch(2.5)
        self.assertEqual(result, 5.0)

    def test_test_singledispatch_none(self):
        manager = DataProcessor()
        result = manager.test_singledispatch(None)
        self.assertEqual(result, "NONE")

    def test_test_singledispatch_unsupported_type(self):
        manager = DataProcessor()
        with self.assertRaises(NotImplementedError):
            manager.test_singledispatch(set())

if __name__ == '__main__':
    unittest.main()
