import time
from tenacity import retry, stop_after_attempt, wait_fixed, retry_if_result

class RetryFunction:
    
    @retry(stop=stop_after_attempt(3), wait=wait_fixed(1), retry=retry_if_result(lambda result: result < 5))
    def might_fail_function(self, value: int, should_raise: bool = False) -> int:
        print(f"The current value is: {value}")
        time.sleep(0.1) 
        if should_raise and value < 5:
            raise ValueError("Value is too small")
        return value