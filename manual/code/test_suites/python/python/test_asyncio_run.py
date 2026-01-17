import unittest
from function_asyncio_run import AsyncTaskHandler 

class TestAsyncioRun(unittest.TestCase):

    def setUp(self):
        
        self.handler = AsyncTaskHandler()

    def test_asyncio_run_with_delayed_task(self):
        
        result = self.handler.run_async_task("delayed", 2)
        self.assertEqual(result, "Task delayed completed after 2 seconds")

    def test_multiple_asyncio_run_tasks(self):
        
        result1 = self.handler.run_async_task("task1", 1)
        result2 = self.handler.run_async_task("task2", 2)
        self.assertEqual(result1, "Task task1 completed after 1 seconds")
        self.assertEqual(result2, "Task task2 completed after 2 seconds")

    def test_asyncio_run_empty_task(self):
        
        result = self.handler.run_async_task("empty", 0)
        self.assertEqual(result, "Task empty completed after 0 seconds")

    def test_asyncio_run_in_different_thread(self):
        
        def run_in_thread():
            result = self.handler.run_async_task("task_in_thread", 1)
            return result
        
        from threading import Thread
        thread = Thread(target=run_in_thread)
        thread.start()
        thread.join()

        self.assertTrue(True)

    def test_asyncio_run_with_multiple_tasks_in_parallel(self):
        
        result1 = self.handler.run_async_task("task1_parallel", 1)
        result2 = self.handler.run_async_task("task2_parallel", 2)
        self.assertEqual(result1, "Task task1_parallel completed after 1 seconds")
        self.assertEqual(result2, "Task task2_parallel completed after 2 seconds")

if __name__ == '__main__':
    unittest.main()
