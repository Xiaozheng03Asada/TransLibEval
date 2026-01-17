from bokeh.plotting import figure, show, output_file

class Plotter:
    def create_scatter_plot(self, x_values: str, y_values: str, title: str = "Scatter Plot") -> str:
        if not x_values or not y_values:
            return "Error: x_values and y_values must not be empty."
        
        try:
            # 将输入的字符串转化为列表
            x_values_list = x_values.strip('[]').split(',')
            y_values_list = y_values.strip('[]').split(',')
            x_values_list = [x.strip().strip('"') for x in x_values_list]
            y_values_list = [int(y.strip()) for y in y_values_list]
        except ValueError:
            return "Error: Invalid input format."
        
        if len(x_values_list) != len(y_values_list):
            return "Error: x_values and y_values must have the same length."
        
        p = figure(title=title, x_axis_label='X Axis', y_axis_label='Y Axis')
        p.scatter(x_values_list, y_values_list, size=8, color="blue", alpha=0.6)
        
        output_file("scatter_plot.html")
        show(p)
        
        return "Plot displayed."
