import unittest
from function_asyncio_sleep import AsyncTaskHandler

class TestAsyncioSleep(unittest.TestCase):

    def setUp(self):
        
        self.handler = AsyncTaskHandler()

    def test_asyncio_run_with_delay(self):
        
        result = self.handler.run_async_task("Delayed", 2)
        self.assertEqual(result, "Task Delayed completed after 2 seconds")

    def test_multiple_asyncio_run_tasks(self):
        
        result1 = self.handler.run_async_task("Task1", 1)
        result2 = self.handler.run_async_task("Task2", 2)
        self.assertEqual(result1, "Task Task1 completed after 1 seconds")
        self.assertEqual(result2, "Task Task2 completed after 2 seconds")

    def test_asyncio_run_with_multiple_delays(self):
        
        result1 = self.handler.run_async_task("Delayed 1", 2)
        result2 = self.handler.run_async_task("Delayed 2", 3)
        self.assertEqual(result1, "Task Delayed 1 completed after 2 seconds")
        self.assertEqual(result2, "Task Delayed 2 completed after 3 seconds")

    def test_asyncio_run_with_float_delay(self):
        
        result = self.handler.run_async_task("Float Delay", 0.5)
        self.assertEqual(result, "Task Float Delay completed after 0.5 seconds")

    def test_asyncio_run_with_custom_task(self):
        
        result = self.handler.run_async_task("Custom Task", 1)
        self.assertEqual(result, "Task Custom Task completed after 1 seconds")

if __name__ == '__main__':
    unittest.main()
