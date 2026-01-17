from bitarray import bitarray

class ConvertToBytesFunction:
    def convert_to_bytes(self, bits_str: str) -> str:
        if not isinstance(bits_str, str) or not all(b in '01' for b in bits_str):
            raise TypeError("Input must be a binary string consisting of '0' and '1'.")
        
        bits = bitarray(bits_str)
        return str(bits.tobytes())
