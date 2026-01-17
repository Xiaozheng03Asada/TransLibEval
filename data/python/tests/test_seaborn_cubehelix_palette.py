import unittest
from function_seaborn_cubehelix_palette import SeabornCubehelixPalette

class TestSeabornCubehelixPalette(unittest.TestCase):

    def test_default_cubehelix(self):
        palette_generator = SeabornCubehelixPalette()
        result = palette_generator.generate_cubehelix()
        self.assertEqual(len(eval(result)), 6)
        self.assertTrue(all(isinstance(color, tuple) and len(color) == 3 for color in eval(result)))

    def test_custom_start_rotation(self):
        palette_generator = SeabornCubehelixPalette()
        result = palette_generator.generate_cubehelix(n_colors=4, start=1.0, rot=0.5)
        self.assertEqual(len(eval(result)), 4)
        self.assertTrue(all(isinstance(color, tuple) and len(color) == 3 for color in eval(result)))

    def test_gamma_correction(self):
        palette_generator = SeabornCubehelixPalette()
        result = palette_generator.generate_cubehelix(n_colors=3, gamma=2.0)
        self.assertEqual(len(eval(result)), 3)
        self.assertTrue(all(isinstance(color, tuple) and len(color) == 3 for color in eval(result)))

    def test_large_palette(self):
        palette_generator = SeabornCubehelixPalette()
        result = palette_generator.generate_cubehelix(n_colors=12)
        self.assertEqual(len(eval(result)), 12)
        self.assertTrue(all(isinstance(color, tuple) and len(color) == 3 for color in eval(result)))

    def test_empty_palette(self):
        palette_generator = SeabornCubehelixPalette()
        result = palette_generator.generate_cubehelix(n_colors=0)
        self.assertEqual(len(eval(result)), 0)

if __name__ == "__main__":
    unittest.main()
