from bokeh.plotting import figure, show, output_file

class Plotter:
    
    def create_bar_chart(self, x_values: str, y_values: str, title: str = "Bar Chart") -> str:
        
        try:
            x_values_list = x_values.strip('[]').split(',')
            y_values_list = y_values.strip('[]').split(',')
            
            x_values_list = [x.strip().strip('"') for x in x_values_list]
            y_values_list = [int(y.strip()) for y in y_values_list]
        except ValueError:
            return "Error: Invalid input format."

        if not x_values_list or not y_values_list:
            return "Error: x_values and y_values must not be empty."
        if len(x_values_list) != len(y_values_list):
            return "Error: x_values and y_values must have the same length."

        p = figure(x_range=x_values_list, title=title, toolbar_location=None, tools="")
        p.vbar(x=x_values_list, top=y_values_list, width=0.9)

        p.xaxis.axis_label = 'X Axis'
        p.yaxis.axis_label = 'Y Axis'

        output_file("bar_chart.html")
        show(p)

        return "Plot displayed."
