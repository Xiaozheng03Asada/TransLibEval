#include <gtest/gtest.h>
#include "../src/function_Pydantic_conint.cpp"

class TestCreateProduct : public ::testing::Test {
protected:
    void SetUp() override {
        validator = new ProductValidator();
    }

    void TearDown() override {
        delete validator;
    }
    
    ProductValidator* validator;
};

TEST_F(TestCreateProduct, ValidProduct) {
    std::string result = validator->create_product(500, 19.99);
    EXPECT_EQ(result, "stock=500 price=19.99");
}

TEST_F(TestCreateProduct, ZeroStock) {
    std::string result = validator->create_product(0, 5.00);
    EXPECT_EQ(result, "stock=0 price=5");
}

TEST_F(TestCreateProduct, StockAtUpperLimit) {
    std::string result = validator->create_product(1000, 15.00);
    EXPECT_EQ(result, "stock=1000 price=15");
}

TEST_F(TestCreateProduct, StockAtLowerLimit) {
    std::string result = validator->create_product(0, 20.00);
    EXPECT_EQ(result, "stock=0 price=20");
}

TEST_F(TestCreateProduct, HighPrice) {
    std::string result = validator->create_product(100, 9999.99);
    EXPECT_EQ(result, "stock=100 price=9999.99");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}