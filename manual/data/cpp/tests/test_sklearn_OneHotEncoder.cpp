#include <gtest/gtest.h>
#include "../src/function_sklearn_OneHotEncoder.cpp"

class TestOneHotEncodingFunction : public ::testing::Test {
protected:
    OneHotEncoderFunction encoder;
};

TEST_F(TestOneHotEncodingFunction, TestFunctionCall) {
    std::string data = "category1 5";
    try {
        std::string result = encoder.test_one_hot_encoding(data);
        EXPECT_EQ(typeid(result).name(), typeid(std::string).name());
    } catch (const std::exception& e) {
        FAIL() << "Function call failed with exception: " << e.what();
    }
}

TEST_F(TestOneHotEncodingFunction, TestCorrectData) {
    std::string result = encoder.test_one_hot_encoding("category1 5");
    EXPECT_EQ(typeid(result).name(), typeid(std::string).name());
}

TEST_F(TestOneHotEncodingFunction, TestEncodedData) {
    std::string result = encoder.test_one_hot_encoding("category1 5");
    std::istringstream iss(result);
    std::string numeric_part, encoded_part;
    std::getline(iss, numeric_part, ',');
    std::getline(iss, encoded_part, ',');
    EXPECT_EQ(numeric_part, "5");
    EXPECT_EQ(encoded_part, "1");
}

TEST_F(TestOneHotEncodingFunction, TestInvalidDataType) {
    EXPECT_THROW(encoder.test_one_hot_encoding("12345"), std::invalid_argument);
}

TEST_F(TestOneHotEncodingFunction, TestMissingCategoricalData) {
    EXPECT_THROW(encoder.test_one_hot_encoding("12"), std::invalid_argument);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}