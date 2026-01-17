import itertools


class CycleProcessor:
    def test_cycle(self, input_sequence: str, num_elements: int) -> str:
        if not input_sequence:
            return ''

        cycle_iter = itertools.cycle(input_sequence)
        result = ""
        for _ in range(num_elements):
            result += next(cycle_iter)
        return result
