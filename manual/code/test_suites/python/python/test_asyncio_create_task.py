import unittest
from function_asyncio_create_task import AsyncTaskHandler

class TestAsyncTaskHandler(unittest.TestCase):

    def setUp(self):
        self.handler = AsyncTaskHandler()

    def test_task_with_delay(self):
        
        result = self.handler.run_task("delayed", 2)
        self.assertEqual(result, "Task delayed completed after 2 seconds")

    def test_multiple_tasks(self):
        
        result1 = self.handler.run_task("task1", 1)
        result2 = self.handler.run_task("task2", 2)
        self.assertEqual(result1, "Task task1 completed after 1 seconds")
        self.assertEqual(result2, "Task task2 completed after 2 seconds")

    def test_task_with_no_delay(self):
        
        result = self.handler.run_task("immediate", 0)
        self.assertEqual(result, "Task immediate completed after 0 seconds")

    def test_task_with_long_delay(self):
        
        result = self.handler.run_task("long_delay", 5)
        self.assertEqual(result, "Task long_delay completed after 5 seconds")

    def test_task_with_invalid_delay(self):
        
        with self.assertRaises(ValueError):
            self.handler.run_task("invalid", -1)

if __name__ == '__main__':
    unittest.main()
