import unittest
from function_statsmodels_tsa_stattools_adfuller import SeasonalDecomposition

class TestADFTest(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        cls.stationary_series = "1,1.2,0.9,1.1,0.95,1.05,0.9,1.1,0.97,1.02"
        cls.non_stationary_series = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15"
        cls.invalid_series = "1,2,3" 

    def test_non_stationary_series(self):
        result = SeasonalDecomposition().perform_adf_test(self.non_stationary_series)
        self.assertIn("Non-Stationary", result)
    
    def test_short_series(self):
        short_series = "1,2,3"
        with self.assertRaises(ValueError):
            SeasonalDecomposition().perform_adf_test(short_series)
    
    def test_invalid_input_type(self):
        invalid_series = "1,2,3,4,5" 
        with self.assertRaises(ValueError):
            SeasonalDecomposition().perform_adf_test(invalid_series)
    
    def test_custom_significance_level(self):
        result = SeasonalDecomposition().perform_adf_test(self.stationary_series, significance_level=0.1)
        self.assertIn("Stationary", result)

    def test_empty_series(self):
        empty_series = ""
        with self.assertRaises(ValueError):
            SeasonalDecomposition().perform_adf_test(empty_series)
    
    @classmethod
    def tearDownClass(cls):
        pass 

if __name__ == "__main__":
    unittest.main()
