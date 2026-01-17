from datetime import datetime

class TimeFormatter:
    def format_current_time(self, year: int, month: int, day: int, hour: int, minute: int, second: int) -> str:
        input_time = datetime(year, month, day, hour, minute, second)
        formatted_time = input_time.strftime("%Y-%m-%d %H:%M:%S")
        return formatted_time