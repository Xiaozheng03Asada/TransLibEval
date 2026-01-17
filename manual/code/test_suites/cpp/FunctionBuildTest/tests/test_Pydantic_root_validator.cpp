#include <gtest/gtest.h>
#include "../src/function_Pydantic_root_validator.cpp"

class TestCreateOrder : public ::testing::Test {
protected:
    void SetUp() override {
        handler = new Order();
    }

    void TearDown() override {
        delete handler;
    }
    
    Order* handler;
};

TEST_F(TestCreateOrder, ValidOrder) {
    std::string result = handler->check_discount_for_large_order(5, 100.0, 0.0);
    EXPECT_EQ(result, "quantity=5 price=100 discount=0");
}

TEST_F(TestCreateOrder, OrderWithLargeQuantityWithoutDiscount) {
    std::string result = handler->check_discount_for_large_order(15, 100.0, 0.0);
    EXPECT_TRUE(result.find("Discount must be greater than 0 for orders with quantity greater than 10") != std::string::npos);
}

TEST_F(TestCreateOrder, OrderWithDiscountButNegativePrice) {
    std::string result = handler->check_discount_for_large_order(5, -50.0, 10.0);
    EXPECT_TRUE(result.find("Price must be positive when discount is applied") != std::string::npos);
}

TEST_F(TestCreateOrder, OrderWithLargeQuantityAndDiscount) {
    std::string result = handler->check_discount_for_large_order(15, 100.0, 10.0);
    EXPECT_EQ(result, "quantity=15 price=100 discount=10");
}

TEST_F(TestCreateOrder, ValidOrderWithoutDiscount) {
    std::string result = handler->check_discount_for_large_order(5, 50.0, 0.0);
    EXPECT_EQ(result, "quantity=5 price=50 discount=0");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}