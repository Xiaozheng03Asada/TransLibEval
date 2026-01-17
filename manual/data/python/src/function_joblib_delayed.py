from joblib import delayed, Parallel


class DelayedExample:
    def apply_delayed_function(self, x: int, y: int) -> str:
        add_func = delayed(lambda a, b: a + b)

        result = Parallel(n_jobs=1)([add_func(x, y)])

        return str(result[0])
