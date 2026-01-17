import itertools

class RepeatExample:
    def repeat_element(self, element, count: int) -> str:
        repeated = itertools.repeat(element, count)
        return ', '.join(map(str, repeated))
