import pyfftw
import numpy as np

class RFFTProcessor:
    def compute_rfft(self, input_array: str) -> str:
        try:
            input_array = eval(input_array)
            if not np.issubdtype(input_array.dtype, np.number):
                raise TypeError("Input array must contain numeric data.")
            result = pyfftw.interfaces.numpy_fft.rfft(input_array)
            return str(result.shape)
        except Exception as e:
            raise ValueError(f"Error in computing RFFT: {e}")
