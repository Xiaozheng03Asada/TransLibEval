#include <gtest/gtest.h>
#include "../src/function_Schematics_model_import_data.cpp"
#include <string>

class TestProductModel : public ::testing::Test {
protected:
    Product product;
};

TEST_F(TestProductModel, test_valid_data) {
    std::string data = "{\"name\": \"Laptop\", \"price\": 1000}";
    std::string expected = "{'name':'Laptop','price':1000,'category':'General'}";
    EXPECT_EQ(product.import_and_validate(data), expected);
}

TEST_F(TestProductModel, test_missing_required_field) {
    std::string data = "{\"price\": 1000}";
    std::string result = product.import_and_validate(data);
    EXPECT_TRUE(result.find("name") != std::string::npos);
}

TEST_F(TestProductModel, test_default_category) {
    std::string data = "{\"name\": \"Tablet\", \"price\": 500}";
    std::string native = product.import_and_validate(data);
    EXPECT_TRUE(native.find("'category':'General'") != std::string::npos);
}

TEST_F(TestProductModel, test_all_fields_provided) {
    std::string data = "{\"name\": \"Headphones\", \"price\": 200, \"category\": \"Electronics\"}";
    std::string expected = "{'name':'Headphones','price':200,'category':'Electronics'}";
    EXPECT_EQ(product.import_and_validate(data), expected);
}

TEST_F(TestProductModel, test_partial_data_with_partial_true) {
    std::string data = "{\"name\": \"Camera\"}";
    std::string result = product.import_and_validate(data);
    EXPECT_TRUE(result.find("price") != std::string::npos);
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}