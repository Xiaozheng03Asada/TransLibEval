class DataProcessor:

    def test_singledispatch(self, data):
        if isinstance(data, int):
            return data * 2
        elif isinstance(data, str):
            return data.upper()
        elif isinstance(data, float):
            return round(data * 2, 2)
        elif isinstance(data, None.__class__):
            return "NONE"
        else:
            raise NotImplementedError(f"不支持的数据类型: {type(data).__name__}")
