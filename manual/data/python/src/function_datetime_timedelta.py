from datetime import datetime, timedelta

class DateTimeModifier:

    def get_modified_datetime(self, original_datetime_str, days=0, hours=0, weeks=0):
        if not isinstance(original_datetime_str, str):
            raise TypeError("original_datetime must be a string")

        try:
            original_datetime = datetime.strptime(original_datetime_str, "%Y-%m-%d %H:%M:%S")
        except ValueError:
            raise ValueError("original_datetime_str format is invalid")

        modified_datetime = original_datetime + timedelta(days=days, hours=hours, weeks=weeks)
        return modified_datetime.strftime("%Y-%m-%d %H:%M:%S")
