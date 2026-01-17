import numpy as np

class IFFTNProcessor:
    
    def compute_ifftn(self, input_array: str) -> str:
        try:
            input_array = eval(input_array)
            result = np.fft.ifftn(input_array)
            return str(result.shape)
        except Exception as e:
            raise ValueError(f"Error in computing IFFT: {e}")
