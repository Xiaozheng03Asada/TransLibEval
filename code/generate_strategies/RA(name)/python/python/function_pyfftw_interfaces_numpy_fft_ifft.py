import pyfftw
import numpy as np

class IFFTProcessor:
    def compute_ifft(self, input_array: str) -> str:
        try:
            input_array = eval(input_array)
            if not np.issubdtype(input_array.dtype, np.complexfloating):
                raise ValueError("Input array must contain complex numbers.")
            result = pyfftw.interfaces.numpy_fft.ifft(input_array)
            return str(result.shape)
        except Exception as e:
            raise ValueError(f"Error in computing IFFT: {e}")
