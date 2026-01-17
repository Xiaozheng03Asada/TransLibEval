import matplotlib.pyplot as plt

class BarPlotGenerator:
    @staticmethod
    def create_bar_plot(categories: str, values: str) -> str:
        
        if not categories or not values:
            return "Error: Categories and values cannot be empty."
        
        categories = categories.split(",")
        values = values.split(",")

        if len(categories) != len(values):
            return "Error: Categories and values must have the same length."

        plt.figure(figsize=(8, 6))
        plt.bar(categories, values, color='skyblue')
        plt.xlabel('Categories')
        plt.ylabel('Values')
        plt.title('Bar Plot')
        plt.savefig('bar_plot.png')
        plt.close()
        return "Plot saved as 'bar_plot.png'."
