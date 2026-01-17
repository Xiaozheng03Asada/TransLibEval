import unittest
from function_datetime_now import DateTimeModifier

class TestDateTimeAPI(unittest.TestCase):

    def setUp(self):
        self.datetime_modifier = DateTimeModifier()

    def test_get_current_datetime_input(self):
        date_string = "2024-11-11 10:30:45"
        result = self.datetime_modifier.get_current_datetime(date_string)
        self.assertEqual(result, "2024-11-11 10:30:45")

    def test_get_current_datetime_default(self):
        result = self.datetime_modifier.get_current_datetime()
        self.assertEqual(len(result), 19)
        self.assertRegex(result, r"\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}")

    def test_get_current_datetime_invalid_format(self):
        date_string = "2024-11-11 10:30"  # Invalid format
        with self.assertRaises(ValueError):
            self.datetime_modifier.get_current_datetime(date_string)

    def test_get_current_datetime_changes(self):
        date_string = "2024-11-11 10:30:45"
        result1 = self.datetime_modifier.get_current_datetime(date_string)
        result2 = self.datetime_modifier.get_current_datetime(date_string)
        self.assertEqual(result1, result2)

    def test_get_current_datetime_empty_string(self):
        date_string = ""
        result = self.datetime_modifier.get_current_datetime(date_string)
        self.assertEqual(result, "1900-01-01 00:00:00")

if __name__ == '__main__':
    unittest.main()
