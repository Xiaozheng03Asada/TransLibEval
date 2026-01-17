import unittest
from function_peewee_get import PeeweeExecutor

class TestPeeweeExecutor(unittest.TestCase):
    def setUp(self):
        self.db_path = "test_db.sqlite"
        self.executor = PeeweeExecutor()

    def test_existing_record(self):
        self.assertEqual(self.executor.get_name_by_id(self.db_path, 1), "Alice")

    def test_existing_record_2(self):
        self.assertEqual(self.executor.get_name_by_id(self.db_path, 2), "Bob")

    def test_nonexistent_record(self):
        self.assertEqual(self.executor.get_name_by_id(self.db_path, 99), "Not Found")

    def test_zero_id(self):
        self.assertEqual(self.executor.get_name_by_id(self.db_path, 0), "Not Found")

    def test_negative_id(self):
        self.assertEqual(self.executor.get_name_by_id(self.db_path, -1), "Not Found")

if __name__ == "__main__":
    unittest.main()
