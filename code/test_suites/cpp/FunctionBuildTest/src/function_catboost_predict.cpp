#include <vector>
#include <stdexcept>
#include <weka/core/Instances.h>
#include <weka/core/DenseInstance.h>
#include <weka/classifiers/trees/RandomForest.h>
#include <weka/core/Attribute.h>

class CatBoostPredictor {
public:
    static int predict_class(float x1, float x2, float x3, float x4, float x5,
                             int y1, int y2, int y3, int y4, int y5, float value) {
        // Input validation for float values
        float features[] = {x1, x2, x3, x4, x5, value};
        for (float f : features) {
            if (std::isnan(f) || std::isinf(f)) {
                throw std::invalid_argument("Feature values and input must be valid integers or floats.");
            }
        }

        // Input validation for integer values
        int labels[] = {y1, y2, y3, y4, y5};
        for (int i : labels) {
            if (i != 0 && i != 1) {
                throw std::invalid_argument("Labels must be either 0 or 1.");
            }
        }

        try {
            // Create attributes for the dataset
            std::vector<weka::core::Attribute*> attributes;
            attributes.push_back(new weka::core::Attribute("feature"));

            // Create nominal class attribute
            std::vector<std::string> classValues = {"0", "1"};
            weka::core::Attribute* classAttribute = new weka::core::Attribute("class", classValues);
            attributes.push_back(classAttribute);

            // Create training dataset
            weka::core::Instances* trainingData = new weka::core::Instances("TrainingData", attributes, 5);
            trainingData->setClassIndex(1);

            // Add training instances
            float trainData[][2] = {
                {x1, static_cast<float>(y1)}, {x2, static_cast<float>(y2)}, {x3, static_cast<float>(y3)},
                {x4, static_cast<float>(y4)}, {x5, static_cast<float>(y5)}
            };

            for (auto& data : trainData) {
                weka::core::DenseInstance* instance = new weka::core::DenseInstance(2);
                instance->setDataset(trainingData);
                instance->setValue(0, data[0]); // feature value
                instance->setClassValue(std::to_string(static_cast<int>(data[1]))); // class value
                trainingData->add(instance);
            }

            // Configure and train the model
            weka::classifiers::trees::RandomForest* model = new weka::classifiers::trees::RandomForest();
            model->setNumIterations(10);
            model->setMaxDepth(3);
            model->buildClassifier(trainingData);

            // Create test instance
            weka::core::Instances* testData = new weka::core::Instances(*trainingData, 0);
            weka::core::DenseInstance* testInstance = new weka::core::DenseInstance(2);
            testInstance->setDataset(testData);
            testInstance->setValue(0, value);
            testData->add(testInstance);

            // Make prediction
            double prediction = model->classifyInstance(testData->firstInstance());
            return static_cast<int>(prediction);

        } catch (std::exception& e) {
            // Convert any unexpected Weka exceptions to invalid_argument
            // if they are related to invalid input
            std::string msg = e.what();
            if (msg.find("bounds") != std::string::npos ||
                msg.find("invalid") != std::string::npos ||
                msg.find("Input") != std::string::npos ||
                msg.find("data") != std::string::npos) {
                throw std::invalid_argument("Invalid input: " + msg);
            }
            throw std::runtime_error("Error during model training or prediction: " + msg);
        }
    }
};