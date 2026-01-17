import unittest
from function_seaborn_set_theme import SeabornThemeSetter

class TestSeabornThemeSetter(unittest.TestCase):
    def test_default_theme(self):
        theme_setter = SeabornThemeSetter()
        result = theme_setter.set_theme()
        self.assertEqual(result, "{'style': 'darkgrid', 'context': 'notebook', 'palette': 'deep'}")

    def test_custom_theme(self):
        theme_setter = SeabornThemeSetter()
        result = theme_setter.set_theme(style="white", context="talk", palette="muted")
        self.assertEqual(result, "{'style': 'white', 'context': 'talk', 'palette': 'muted'}")

    def test_empty_style(self):
        theme_setter = SeabornThemeSetter()
        result = theme_setter.set_theme(style="")
        self.assertEqual(result, "{'style': 'darkgrid', 'context': 'notebook', 'palette': 'deep'}")

    def test_empty_context(self):
        theme_setter = SeabornThemeSetter()
        result = theme_setter.set_theme(context="")
        self.assertEqual(result, "{'style': 'darkgrid', 'context': 'notebook', 'palette': 'deep'}")

    def test_empty_palette(self):
        theme_setter = SeabornThemeSetter()
        result = theme_setter.set_theme(palette="")
        self.assertEqual(result, "{'style': 'darkgrid', 'context': 'notebook', 'palette': 'deep'}")

if __name__ == "__main__":
    unittest.main()
