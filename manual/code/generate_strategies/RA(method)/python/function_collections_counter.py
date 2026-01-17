from collections import Counter


class CounterCalculator:
    def count_elements(self, data: str) -> str:
        counter = Counter(data.split())

        if not counter:
            return "failed"

        result = ', '.join(f"{key}:{value}" for key, value in counter.items())
        return result
