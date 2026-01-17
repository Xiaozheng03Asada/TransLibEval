from bitarray import bitarray

class BitarrayExtender:
    def extend_bits(self, bits: str, extra_bits: str) -> str:
        if not isinstance(bits, str) or not isinstance(extra_bits, str):
            raise TypeError("Both inputs must be strings of '0' and '1'.")
        if any(c not in '01' for c in bits + extra_bits):
            raise ValueError("Both inputs must only contain '0' and '1'.")

        ba = bitarray(bits)
        ba.extend(bitarray(extra_bits))
        return ba.to01()  
