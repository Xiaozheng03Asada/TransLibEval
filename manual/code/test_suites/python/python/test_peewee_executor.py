import unittest
from function_peewee_executor import PeeweeExecutor

class TestPeeweeExecutor(unittest.TestCase):
    def setUp(self):
        self.executor = PeeweeExecutor()
        self.db_path = "test_db.sqlite"

    def test_insert_query(self):
        result = self.executor.execute_query(self.db_path, "INSERT INTO testmodel (name) VALUES ('Alice')")
        self.assertEqual(result, "0")

    def test_select_query(self):
        result = self.executor.execute_query(self.db_path, "SELECT COUNT(*) FROM testmodel")
        self.assertTrue(result.isdigit())

    def test_update_query(self):
        self.executor.execute_query(self.db_path, "INSERT INTO testmodel (name) VALUES ('Bob')")
        result = self.executor.execute_query(self.db_path, "UPDATE testmodel SET name='Charlie' WHERE name='Bob'")
        self.assertEqual(result, "0")

    def test_delete_query(self):
        self.executor.execute_query(self.db_path, "INSERT INTO testmodel (name) VALUES ('David')")
        result = self.executor.execute_query(self.db_path, "DELETE FROM testmodel WHERE name='David'")
        self.assertEqual(result, "0")

    def test_invalid_query(self):
        with self.assertRaises(Exception):
            self.executor.execute_query(self.db_path, "INVALID SQL")

if __name__ == '__main__':
    unittest.main()
