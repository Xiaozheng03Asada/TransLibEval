#include <gtest/gtest.h>

#include "../src/function_marshmallow_pre_load.cpp"  

TEST(TestProductProcessor, PreLoadConversion) {
    ProductProcessor processor;
    std::string product_data = R"({"name": "tablet", "price": 199.99})";
    std::string result = processor.process_product_data(product_data);
    EXPECT_NE(result.find("\"name\":\"TABLET\""), std::string::npos);
    EXPECT_NE(result.find("\"price\":199.99"), std::string::npos);
}

TEST(TestProductProcessor, InvalidDataType) {
    ProductProcessor processor;
    std::string product_data = R"({"name": 12345, "price": 199.99})";
    EXPECT_EQ(processor.process_product_data(product_data), "Error: invalid input");
}

TEST(TestProductProcessor, InvalidPrice) {
    ProductProcessor processor;
    std::string product_data = R"({"name": "tablet", "price": "not_a_number"})";
    EXPECT_EQ(processor.process_product_data(product_data), "Error: invalid input");
}

TEST(TestProductProcessor, MissingNameField) {
    ProductProcessor processor;
    std::string product_data = R"({"price": 199.99, "in_stock": true})";
    EXPECT_EQ(processor.process_product_data(product_data), "Error: invalid input");
}

TEST(TestProductProcessor, MissingPriceField) {
    ProductProcessor processor;
    std::string product_data = R"({"name": "tablet", "in_stock": true})";
    EXPECT_EQ(processor.process_product_data(product_data), "Error: invalid input");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}