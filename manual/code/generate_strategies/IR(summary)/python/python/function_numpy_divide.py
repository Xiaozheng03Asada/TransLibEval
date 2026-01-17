import numpy as np


class DivisionCalculator:

    def divide(self, arr1, arr2=None, scalar=None):
        try:
            if arr2 is not None:  
                
                if not isinstance(arr1, (int, float)) or not isinstance(arr2, (int, float)):
                    raise ValueError("Both arr1 and arr2 must be basic data types (int or float).")

                
                result = arr1 / arr2
                return result  

            elif scalar is not None:  
                if not isinstance(arr1, (int, float)):
                    raise ValueError("arr1 must be a basic data type (int or float) when dividing by scalar.")

                
                result = arr1 / scalar
                return result  

            else:
                raise ValueError("Either arr2 or scalar must be provided.")

        except Exception as e:
            raise ValueError(f"Error in division: {e}")