from functools import cmp_to_key

class NumberDictManager:

    def manage_number_dict(self, operations_str):
        operations = operations_str.split(';')
        number_dict = {}

        def compare_numbers(a, b):
            return a - b  # 比较函数，按升序排序数字

        for op in operations:
            op_parts = op.split(',')
            if op_parts[0] == "add":
                key = int(op_parts[1])
                value = int(op_parts[2])
                number_dict[key] = value
            elif op_parts[0] == "remove":
                key = int(op_parts[1])
                if key in number_dict:
                    del number_dict[key]
            elif op_parts[0] == "get":
                key = int(op_parts[1])
                return str(number_dict.get(key, "default"))
            elif op_parts[0] == "sort":
                sorted_items = sorted(number_dict.items(), key=cmp_to_key(lambda x, y: compare_numbers(x[0], y[0])))
                return " ".join([f"{k}:{v}" for k, v in sorted_items])
            elif op_parts[0] == "sum":
                return str(sum(number_dict.values()))

        return ""
