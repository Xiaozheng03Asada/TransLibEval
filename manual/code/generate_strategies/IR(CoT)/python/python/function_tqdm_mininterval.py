from tqdm import tqdm
import time

class ProgressBar:
    @staticmethod
    def might_fail_function(mininterval_value: float) -> int:
        results = []
        for i in tqdm(range(10), mininterval=mininterval_value):
            time.sleep(0.1)
            results.append(i)
        return len(results)
