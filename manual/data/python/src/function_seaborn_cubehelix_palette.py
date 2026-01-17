import seaborn as sns

class SeabornCubehelixPalette:
    def generate_cubehelix(self, n_colors: int = 6, start: float = 0.5, rot: float = -1.5, gamma: float = 1.0) -> str:
        palette = sns.cubehelix_palette(n_colors=n_colors, start=start, rot=rot, gamma=gamma, as_cmap=False)
        return str([tuple(color) for color in palette])
