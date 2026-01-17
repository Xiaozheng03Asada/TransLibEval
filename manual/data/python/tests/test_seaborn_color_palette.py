import unittest
from function_seaborn_color_palette import SeabornColorPalette

class TestSeabornColorPalette(unittest.TestCase):

    def test_default_palette(self):
        palette_generator = SeabornColorPalette()
        result = palette_generator.generate_palette()
        self.assertEqual(len(eval(result)), 6)
        self.assertTrue(all(isinstance(color, tuple) and len(color) == 3 for color in eval(result)))

    def test_bright_palette(self):
        palette_generator = SeabornColorPalette()
        result = palette_generator.generate_palette(palette_name="bright", n_colors=5)
        self.assertEqual(len(eval(result)), 5)
        self.assertTrue(all(isinstance(color, tuple) and len(color) == 3 for color in eval(result)))

    def test_colorblind_palette(self):
        palette_generator = SeabornColorPalette()
        result = palette_generator.generate_palette(palette_name="colorblind", n_colors=7)
        self.assertEqual(len(eval(result)), 7)
        self.assertTrue(all(isinstance(color, tuple) and len(color) == 3 for color in eval(result)))

    def test_empty_palette(self):
        palette_generator = SeabornColorPalette()
        result = palette_generator.generate_palette(n_colors=0)
        self.assertEqual(len(eval(result)), 0)

    def test_large_palette(self):
        palette_generator = SeabornColorPalette()
        result = palette_generator.generate_palette(palette_name="muted", n_colors=15)
        self.assertEqual(len(eval(result)), 15)
        self.assertTrue(all(isinstance(color, tuple) and len(color) == 3 for color in eval(result)))

if __name__ == "__main__":
    unittest.main()
