import seaborn as sns

class SeabornThemeSetter:
    def set_theme(self, style="darkgrid", context="notebook", palette="deep") -> str:
        
        valid_styles = ["white", "dark", "whitegrid", "darkgrid", "ticks"]
        valid_contexts = ["paper", "notebook", "talk", "poster"]
        valid_palettes = ["deep", "muted", "bright", "pastel", "dark", "colorblind"]

        if style not in valid_styles:
            style = "darkgrid"  
        if context not in valid_contexts:
            context = "notebook" 
        if palette not in valid_palettes:
            palette = "deep"
        
        sns.set_theme(style=style, context=context, palette=palette)
        return f"{{'style': '{style}', 'context': '{context}', 'palette': '{palette}'}}"
