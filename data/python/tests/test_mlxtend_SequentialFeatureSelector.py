import unittest
from function_mlxtend_SequentialFeatureSelector import FeatureSelector

class TestFeatureSelector(unittest.TestCase):

    def test_single_feature(self):
        fs = FeatureSelector()
        result = fs.sequential_feature_selection('sepal length', 'species', 1)
        self.assertEqual(result, "['petal width (cm)']")

    def test_two_features(self):
        fs = FeatureSelector()
        result = fs.sequential_feature_selection('sepal length', 'species', 2)
        self.assertEqual(result, "['petal length (cm)', 'petal width (cm)']")

    def test_all_features(self):
        fs = FeatureSelector()
        result = fs.sequential_feature_selection('sepal length', 'species', 4)
        self.assertEqual(result, "['petal length (cm)', 'petal width (cm)', 'sepal length (cm)', 'sepal width (cm)']")

    def test_invalid_feature_count(self):
        fs = FeatureSelector()
        result = fs.sequential_feature_selection('sepal length', 'species', 0)
        self.assertEqual(result, "[]")

    def test_no_features(self):
        fs = FeatureSelector()
        result = fs.sequential_feature_selection('sepal length', 'species', 5)
        self.assertEqual(result, "[]")

if __name__ == '__main__':
    unittest.main()
