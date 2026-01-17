#include <gtest/gtest.h>

#include "../src/function_marshmallow_load.cpp" 

TEST(TestDeserializeProductData, ValidData) {
    ProductSchema schema;
    std::string product_data = R"({"name": "Laptop", "price": 999.99, "in_stock": true})";
    std::string expected = R"({"name":"Laptop","price":999.99,"in_stock":true})";
    EXPECT_EQ(schema.deserialize_product_data(product_data), expected);
}

TEST(TestDeserializeProductData, MissingRequiredFields) {
    ProductSchema schema;
    std::string product_data = R"({"price": 499.99})";
    EXPECT_EQ(schema.deserialize_product_data(product_data), "Error: invalid input");
}

TEST(TestDeserializeProductData, DefaultFieldValue) {
    ProductSchema schema;
    std::string product_data = R"({"name": "Tablet", "price": 199.99})";
    std::string expected = R"({"name":"Tablet","price":199.99,"in_stock":true})";
    EXPECT_EQ(schema.deserialize_product_data(product_data), expected);
}

TEST(TestDeserializeProductData, InvalidDataType) {
    ProductSchema schema;
    std::string product_data = R"({"name": "Chair", "price": "forty"})";
    EXPECT_EQ(schema.deserialize_product_data(product_data), "Error: invalid input");
}

TEST(TestDeserializeProductData, ProductWithOnlyNameAndPrice) {
    ProductSchema schema;
    std::string product_data = R"({"name": "Phone", "price": 799.99})";
    std::string expected = R"({"name":"Phone","price":799.99,"in_stock":true})";
    EXPECT_EQ(schema.deserialize_product_data(product_data), expected);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}