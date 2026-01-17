import unittest
from function_peewee_execute import PeeweeExecutor

class TestPeeweeExecute(unittest.TestCase):
    def setUp(self):
        self.executor = PeeweeExecutor()
        self.db_path = "test_db.sqlite"

    def test_create_table(self):
        result = self.executor.execute_query(self.db_path, "CREATE TABLE IF NOT EXISTS test (name TEXT);")
        self.assertEqual(result, "-1")

    def test_insert_data(self):
        result = self.executor.execute_query(self.db_path, "INSERT INTO test (name) VALUES ('Alice');")
        self.assertEqual(result, "1")

    def test_multiple_inserts(self):
        result = self.executor.execute_query(self.db_path, "INSERT INTO test (name) VALUES ('Bob'), ('Charlie');")
        self.assertEqual(result, "2")

    def test_update_data(self):
        self.executor.execute_query(self.db_path, "INSERT INTO test (name) VALUES ('David');")
        result = self.executor.execute_query(self.db_path, "UPDATE test SET name = 'Dave' WHERE name = 'David';")
        self.assertEqual(result, "1")

    def test_delete_data(self):
        self.executor.execute_query(self.db_path, "INSERT INTO test (name) VALUES ('Eve');")
        result = self.executor.execute_query(self.db_path, "DELETE FROM test WHERE name = 'Eve';")
        self.assertEqual(result, "1")

if __name__ == '__main__':
    unittest.main()
