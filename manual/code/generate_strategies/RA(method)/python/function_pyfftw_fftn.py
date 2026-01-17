import pyfftw.interfaces

class FFTNProcessor:
    
    def compute_fftn(self, input_array: str) -> str:
        try:
            input_array = eval(input_array)
            fftn_result = pyfftw.interfaces.numpy_fft.fftn(input_array)
            return str(fftn_result.shape)
        except Exception as e:
            return f"Error in computing FFTN: {e}"
