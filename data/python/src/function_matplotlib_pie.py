import matplotlib.pyplot as plt

class PieChartGenerator:
    @staticmethod
    def create_pie_chart(labels: str, sizes: str, title="Pie Chart") -> str:
        
        if not labels or not sizes:
            return "Error: labels and sizes cannot be empty."
        
        labels = labels.split(",")
        sizes = sizes.split(",")
        
        try:
            sizes = [float(size.strip()) for size in sizes]
            if any(size < 0 for size in sizes):
                return "Error: All sizes must be non-negative numbers."
        except ValueError:
            return "Error: All sizes must be non-negative numbers."
        
        if len(labels) != len(sizes):
            return "Error: labels and sizes must have the same length."

        plt.figure(figsize=(8, 6))
        plt.pie(sizes, labels=labels, autopct='%1.1f%%', startangle=90, colors=plt.cm.Paired.colors)
        plt.title(title)
        plt.savefig('pie_chart.png')
        plt.close()
        return "Pie chart saved as 'pie_chart.png'."
