import seaborn as sns

class SeabornKDEPlot:
    def generate_kdeplot(self, data: str, shade: bool = True, color: str = 'blue') -> str:
        if not isinstance(data, str):
            raise TypeError("Input data must be a string representing a list of numbers.")
        
        if not data.strip():
            raise ValueError("Input data cannot be empty.")

        try:
            data = [float(i) for i in data.split(",")]
        except ValueError:
            raise TypeError("Input data must be a string with numeric values separated by commas.")

        sns.kdeplot(data=data, shade=shade, color=color)
        return f"{{'data': '{','.join(map(str, data))}', 'shade': {shade}, 'color': '{color}'}}"
