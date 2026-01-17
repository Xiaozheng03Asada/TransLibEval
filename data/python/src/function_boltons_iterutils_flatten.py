from boltons.iterutils import flatten


class DataFlattener:
    def flatten_data(self, data_str):
        if not isinstance(data_str, str):
            return "Error"
        try:
            data = [[int(x) for x in part.split(',')] for part in data_str.split(';')]
            flattened_data = list(flatten(data))
            return ",".join(map(str, flattened_data))
        except Exception:
            return "Error"
