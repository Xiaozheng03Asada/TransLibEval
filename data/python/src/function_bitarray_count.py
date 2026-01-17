from bitarray import bitarray


class CountBitsFunction:
    def count_bits(self, bits, value=1):
        if not isinstance(bits, str):
            raise TypeError("Input must be a string of '0' and '1'.")
        if value not in (0, 1):
            raise ValueError("Value must be either 0 or 1.")

        ba = bitarray(bits)
        return ba.count(value)
