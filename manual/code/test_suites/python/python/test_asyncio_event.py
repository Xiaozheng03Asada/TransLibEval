import unittest
from function_asyncio_event import EventTaskHandler

class TestEventTaskHandler(unittest.TestCase):

    def test_task_when_event_triggered(self):
        result = EventTaskHandler.might_fail_function("triggered", "A")
        self.assertEqual(result, "Task A completed")

    def test_task_when_event_not_triggered(self):
        result = EventTaskHandler.might_fail_function("waiting", "B")
        self.assertEqual(result, "Task B is waiting for the event")
        
    def test_task_with_empty_event_status(self):
        result = EventTaskHandler.might_fail_function("", "C")
        self.assertEqual(result, "Task C is waiting for the event")

    def test_task_with_empty_task_name(self):
        result = EventTaskHandler.might_fail_function("triggered", "")
        self.assertEqual(result, "Task  completed")

    def test_task_with_non_triggered_event_status(self):
        result = EventTaskHandler.might_fail_function("inactive", "D")
        self.assertEqual(result, "Task D is waiting for the event")

if __name__ == '__main__':
    unittest.main()
