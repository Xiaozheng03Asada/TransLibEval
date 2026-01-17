#include <vector>
#include <stdexcept>
#include <weka/classifiers/functions/Logistic.h>
#include <weka/core/Attribute.h>
#include <weka/core/DenseInstance.h>
#include <weka/core/Instances.h>

class CatBoostProbabilityPredictor {
public:
    float predict_probability(float x1, float x2, float x3, float x4, float x5,
                              int y1, int y2, int y3, int y4, int y5, float value) {
        class InternalPredictor {
        private:
            void validateInputs(float x1, float x2, float x3, float x4, float x5,
                               int y1, int y2, int y3, int y4, int y5, float value) {
                std::vector<float> numbers = {x1, x2, x3, x4, x5, value};
                for (float num : numbers) {
                    if (std::isnan(num)) {
                        throw std::invalid_argument("Feature values and input must be integers or floats.");
                    }
                }
            }

        public:
            float predictProbability(float x1, float x2, float x3, float x4, float x5,
                                     int y1, int y2, int y3, int y4, int y5, float value) {
                validateInputs(x1, x2, x3, x4, x5, y1, y2, y3, y4, y5, value);

                try {
                    std::vector<weka::core::Attribute> attributes;
                    attributes.push_back(weka::core::Attribute("feature"));

                    std::vector<std::string> classValues = {"0", "1"};
                    attributes.push_back(weka::core::Attribute("class", classValues));

                    weka::core::Instances trainingData("TrainingData", attributes, 5);
                    trainingData.setClassIndex(1);

                    std::vector<float> trainValues = {x1, x2, x3, x4, x5};
                    std::vector<int> trainLabels = {y1, y2, y3, y4, y5};

                    for (int i = 0; i < 5; i++) {
                        weka::core::DenseInstance instance(2);
                        instance.setValue(0, trainValues[i]);
                        instance.setValue(1, trainLabels[i]);
                        trainingData.add(instance);
                    }

                    weka::classifiers::functions::Logistic model;
                    model.setMaxIts(10);
                    model.setRidge(0.1);
                    model.buildClassifier(trainingData);

                    weka::core::Instances testData("TestData", attributes, 1);
                    testData.setClassIndex(1);
                    weka::core::DenseInstance testInstance(2);
                    testInstance.setValue(0, value);
                    testData.add(testInstance);

                    double* probabilities = model.distributionForInstance(testData.instance(0));
                    return static_cast<float>(probabilities[1]);
                } catch (std::exception& e) {
                    throw std::invalid_argument("Error in model prediction: " + std::string(e.what()));
                }
            }
        };

        InternalPredictor predictor;
        return predictor.predictProbability(x1, x2, x3, x4, x5, y1, y2, y3, y4, y5, value);
    }
};