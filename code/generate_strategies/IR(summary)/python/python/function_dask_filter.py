from dask import delayed, compute

class FilterEvenNumbersDask:
    def check_discount_for_large_order(self, data_str: str) -> str:
        try:
            if data_str.strip() == "":  
                return ""
            data = list(map(int, data_str.split(",")))
            delayed_task = delayed(lambda x: [num for num in x if num % 2 == 0])(data)
            return ",".join(map(str, compute(delayed_task)[0]))
        except Exception:
            return "Error"
