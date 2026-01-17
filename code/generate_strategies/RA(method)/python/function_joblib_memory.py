from joblib import Memory


class MemoryExample:
    def compute_square(self, x: int) -> int:
        memory = Memory(location=None, verbose=0)

        cached_compute = memory.cache(lambda x: x * x)

        return cached_compute(x)
