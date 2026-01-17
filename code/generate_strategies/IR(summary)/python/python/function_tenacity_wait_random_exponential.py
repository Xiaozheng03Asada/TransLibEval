import time
from tenacity import retry, stop_after_attempt, wait_random_exponential

class RetryFunction:

    @retry(stop=stop_after_attempt(3), wait=wait_random_exponential(multiplier=1, min=1, max=10))
    def might_fail_function(self, value: int, extra_sleep: float = 0, handle_retry_error: bool = False) -> int:
        if extra_sleep:
            time.sleep(extra_sleep)
        else:
            time.sleep(0.1)

        print(f"The current value is: {value}")
        if value < 5:
            raise ValueError("Value is too small")
        return value
