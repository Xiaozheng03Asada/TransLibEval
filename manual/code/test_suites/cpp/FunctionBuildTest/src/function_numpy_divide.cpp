#include <stdexcept>
#include <typeinfo>

class DivisionCalculator {
public:
    static double divide(const Number& arr1, const Number& arr2, const Number& scalar) {
        class InnerCalculator {
        public:
            double divideArrays(const Number& a1, const Number& a2) {
                if (a2.isNull()) {
                    throw std::invalid_argument("arr2 must not be null for this operation.");
                }
                if (!a1.isNumber() || !a2.isNumber()) {
                    throw std::invalid_argument("Both arr1 and arr2 must be basic data types (int or float).");
                }
                return a1.toDouble() / a2.toDouble();
            }

            double divideByScalar(const Number& a1, const Number& scalarValue) {
                if (scalarValue.isNull()) {
                    throw std::invalid_argument("Scalar must not be null for this operation.");
                }
                if (!a1.isNumber()) {
                    throw std::invalid_argument("arr1 must be a basic data type (int or float) when dividing by scalar.");
                }
                return a1.toDouble() / scalarValue.toDouble();
            }
        };

        InnerCalculator calculator;

        if (!arr2.isNull()) {
            return calculator.divideArrays(arr1, arr2);
        } else if (!scalar.isNull()) {
            return calculator.divideByScalar(arr1, scalar);
        } else {
            throw std::invalid_argument("Either arr2 or scalar must be provided.");
        }
    }
};