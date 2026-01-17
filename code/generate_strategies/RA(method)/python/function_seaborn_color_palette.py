import seaborn as sns

class SeabornColorPalette:
    def generate_palette(self, palette_name: str = "deep", n_colors: int = 6) -> str:
        palette = sns.color_palette(palette_name, n_colors)
        return str([tuple(color) for color in palette])
