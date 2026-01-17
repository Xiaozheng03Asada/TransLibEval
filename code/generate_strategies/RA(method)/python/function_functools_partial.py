from functools import partial

class PartialFunctionExample:
    def apply_partial_function(self, base_value: int, add_value: int) -> int:
        add_base = partial(lambda base, add: base + add, base_value)
        return add_base(add_value)
