import unittest
from function_peewee_insert import PeeweeInsert

class TestPeeweeInsert(unittest.TestCase):
    def setUp(self):
        self.executor = PeeweeInsert()
        self.db_path = "test.db"

    def test_valid_insert(self):
        result = self.executor.insert_record(self.db_path, "Alice", 25)
        self.assertTrue(result.isdigit())  # 应返回插入记录的ID

    def test_empty_name(self):
        result = self.executor.insert_record(self.db_path, "", 30)
        self.assertTrue(result.isdigit())  # 即使名字为空，也应该成功插入

    def test_zero_age(self):
        result = self.executor.insert_record(self.db_path, "Bob", 0)
        self.assertTrue(result.isdigit())  # 允许年龄为0

    def test_negative_age(self):
        result = self.executor.insert_record(self.db_path, "Charlie", -5)
        self.assertTrue(result.isdigit())  # 允许负数，但数据库应处理

    def test_large_age(self):
        result = self.executor.insert_record(self.db_path, "Dave", 150)
        self.assertTrue(result.isdigit())  # 允许较大年龄值

if __name__ == "__main__":
    unittest.main()
