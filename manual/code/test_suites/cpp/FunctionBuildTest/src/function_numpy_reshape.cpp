#include <string>
#include <iomanip>
#include <sstream>
#include <nd4j/NDArray.h>
#include <nd4j/NDArrayFactory.h>

class ReshapeCalculator {
public:
    std::string reshape(double price, double quantity) {
        class InnerCalculator {
        public:
            std::string calculate(double price, double quantity) {
                if (price == 0.0 || quantity == 0.0) {
                    price = 10.0;
                    quantity = 5.0;
                }

                double data[] = {price, quantity};
                auto array = nd4j::NDArrayFactory::create(data, {2});
                auto reshaped = array.reshape({1, 2});

                double totalAmount = reshaped.getDouble(0, 0) * reshaped.getDouble(0, 1);

                std::ostringstream result;
                result << "Price: " << reshaped.getDouble(0, 0)
                       << ", Quantity: " << reshaped.getDouble(0, 1)
                       << ", Total Amount: " << totalAmount;

                return result.str();
            }
        };

        InnerCalculator calculator;
        return calculator.calculate(price, quantity);
    }
};