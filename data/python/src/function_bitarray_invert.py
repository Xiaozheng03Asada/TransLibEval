from bitarray import bitarray

class InvertBitsFunction:
    def invert_bitarray(self, bits: str, start: int = None, stop: int = None) -> str:
        if not isinstance(bits, str) or not all(c in '01' for c in bits):
            raise TypeError("Input must be a string containing only '0' and '1'.")
        if start is not None and not isinstance(start, int):
            raise TypeError("Start index must be an integer or None.")
        if stop is not None and not isinstance(stop, int):
            raise TypeError("Stop index must be an integer or None.")
        if start is not None and stop is not None and (start < 0 or stop > len(bits) or start >= stop):
            raise ValueError("Invalid start and stop indices.")

        bit_array = bitarray(bits)

        if start is None and stop is None:
            bit_array.invert()
        else:
            for i in range(start, stop):
                bit_array[i] = not bit_array[i]

        return bit_array.to01()
