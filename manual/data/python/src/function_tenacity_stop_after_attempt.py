import time
from tenacity import retry, stop_after_attempt, wait_fixed

class RetryFunction:
    @retry(stop=stop_after_attempt(3), wait=wait_fixed(1), retry=(lambda e: isinstance(e, ValueError)))
    def might_fail_function(self, value: int, extra_sleep: float = 0, handle_retry_error: bool = False) -> int:
        try:
            print(f"The current value is: {value}")
            time.sleep(extra_sleep)
            if value < 5:
                raise ValueError("Value is too small")
            return value
        except ValueError as e:
            if handle_retry_error:
                raise RuntimeError(f"Operation failed after retries: {e}") from e
            raise
