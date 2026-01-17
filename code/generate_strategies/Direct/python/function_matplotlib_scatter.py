import matplotlib.pyplot as plt

class ScatterPlotGenerator:
    @staticmethod
    def create_scatter_plot(x_values: str, y_values: str, title="Scatter Plot") -> str:
        
        if not x_values or not y_values:
            return "Error: x_values and y_values cannot be empty."
        
        x_values = x_values.split(",")
        y_values = y_values.split(",")
        
        try:
            x_values = [float(val.strip()) for val in x_values]
            y_values = [float(val.strip()) for val in y_values]
        except ValueError:
            return "Error: All x and y values must be numbers."
        
        if len(x_values) != len(y_values):
            return "Error: x_values and y_values must have the same length."

        plt.figure(figsize=(8, 6))
        plt.scatter(x_values, y_values, color='blue', alpha=0.7)
        plt.xlabel('X Values')
        plt.ylabel('Y Values')
        plt.title(title)
        plt.savefig('scatter_plot.png')
        plt.close()
        return "Plot saved as 'scatter_plot.png'."
