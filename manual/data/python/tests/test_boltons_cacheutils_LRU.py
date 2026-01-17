import unittest
from function_boltons_cacheutils_LRU import LRUCacheManager

class TestBoltonsLRU(unittest.TestCase):
    def setUp(self):
        self.function = LRUCacheManager()

    def test_basic_set_and_get(self):
        operations_str = "set,a,1,set,b,2,get,a,get,b"
        result = self.function.manage_cache_operations(3, operations_str)
        expected = "1,2"
        self.assertEqual(result, expected)

    def test_lru_eviction(self):
        operations_str = "set,a,1,set,b,2,set,c,3,set,d,4,get,a,get,b"
        result = self.function.manage_cache_operations(3, operations_str)
        expected = "None,2"
        self.assertEqual(result, expected)

    def test_update_existing_key(self):
        operations_str = "set,a,1,set,a,10,get,a"
        result = self.function.manage_cache_operations(2, operations_str)
        expected = "10"
        self.assertEqual(result, expected)

    def test_cache_size_limit(self):
        operations_str = "set,a,1,set,b,2,set,c,3,set,d,4,get,a,get,b,get,c,get,d"
        result = self.function.manage_cache_operations(2, operations_str)
        expected = "None,None,3,4"
        self.assertEqual(result, expected)

    def test_empty_cache_operations(self):
        operations_str = ""
        result = self.function.manage_cache_operations(2, operations_str)
        expected = ""
        self.assertEqual(result, expected)

if __name__ == '__main__':
    unittest.main()
