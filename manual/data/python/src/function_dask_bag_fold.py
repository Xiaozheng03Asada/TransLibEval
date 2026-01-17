from dask import bag

class CumulativeSumFunction:
    def cumulative_sum(self, input_str: str) -> str:
        try:
            input_list = list(map(float, input_str.split(",")))
            dask_bag = bag.from_sequence(input_list)
            result = dask_bag.fold(lambda x, y: x + y).compute()
            return str(result)
        except Exception as e:
            return "Error"
