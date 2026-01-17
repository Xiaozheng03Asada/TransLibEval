from bitarray import bitarray

class BitarrayExtender:
    def extend_bits(self, bits: str, values: str) -> str:
        if not isinstance(bits, str): 
            raise TypeError("Input must be a string of '0' and '1'.")
        if not all(v in ('0', '1') for v in values):
            raise ValueError("All elements in the iterable must be '0' or '1'.")
        
        ba = bitarray(bits)
        ba.extend(values)  
        return ba.to01()
