from mlxtend.plotting import plot_learning_curves
import numpy as np
import matplotlib.pyplot as plt
from sklearn.model_selection import train_test_split
from sklearn.datasets import load_digits
from sklearn.svm import SVC

class LearningCurvePlotter:
    def plot_learning_curve(self, n_samples: int, n_features: int, n_classes: int) -> str:
        if n_samples <= 0 or n_samples > len(load_digits().data):
            return "Invalid number of samples"
        
        digits = load_digits()
        X, y = digits.data, digits.target
        X_train, X_test, y_train, y_test = train_test_split(X, y, train_size=n_samples, test_size=0.2, random_state=42)

        model = SVC(kernel='linear')
        
        plot_learning_curves(X_train, y_train, X_test, y_test, model, scoring='accuracy')
        plt.show()

        return "Learning curve plotted successfully"
