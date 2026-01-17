import unittest
from function_datetime_timedelta import DateTimeModifier

class TestDateTimeOperations(unittest.TestCase):

    def setUp(self):
        self.datetime_modifier = DateTimeModifier()

    def test_add_days(self):
        result = self.datetime_modifier.get_modified_datetime("2024-11-11 10:30:45", days=5)
        self.assertEqual(result, "2024-11-16 10:30:45")

    def test_subtract_days(self):
        result = self.datetime_modifier.get_modified_datetime("2024-11-11 10:30:45", days=-3)
        self.assertEqual(result, "2024-11-08 10:30:45")

    def test_add_hours(self):
        result = self.datetime_modifier.get_modified_datetime("2024-11-11 10:30:45", hours=5)
        self.assertEqual(result, "2024-11-11 15:30:45")

    def test_subtract_hours(self):
        result = self.datetime_modifier.get_modified_datetime("2024-11-11 10:30:45", hours=-5)
        self.assertEqual(result, "2024-11-11 05:30:45")

    def test_add_weeks(self):
        result = self.datetime_modifier.get_modified_datetime("2024-11-11 10:30:45", weeks=2)
        self.assertEqual(result, "2024-11-25 10:30:45")

if __name__ == '__main__':
    unittest.main()
