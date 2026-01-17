import unittest
from function_functools_wraps import LRUCacheManager

class TestLRUCacheManager(unittest.TestCase):

    def test_manage_lru_cache(self):
        manager = LRUCacheManager()
        cache_size = "3"
        operations = "set,a,1;set,b,2;get,a"
        result = manager.manage_lru_cache(cache_size, operations)
        self.assertEqual(result, "1")

    def test_manage_lru_cache_with_eviction(self):
        manager = LRUCacheManager()
        cache_size = "2"
        operations = "set,a,1;set,b,2;set,c,3;get,a"
        result = manager.manage_lru_cache(cache_size, operations)
        self.assertEqual(result, "None")

    def test_manage_lru_cache_with_multiple_gets(self):
        manager = LRUCacheManager()
        cache_size = "2"
        operations = "set,a,1;set,b,2;get,a;get,b"
        result = manager.manage_lru_cache(cache_size, operations)
        self.assertEqual(result, "1,2")

    def test_manage_lru_cache_with_empty_operations(self):
        manager = LRUCacheManager()
        cache_size = "3"
        operations = ""
        result = manager.manage_lru_cache(cache_size, operations)
        self.assertEqual(result, "")

    def test_manage_lru_cache_with_non_existent_key(self):
        manager = LRUCacheManager()
        cache_size = "3"
        operations = "get,nonexistent_key"
        result = manager.manage_lru_cache(cache_size, operations)
        self.assertEqual(result, "None")

if __name__ == '__main__':
    unittest.main()
