#include <gtest/gtest.h>
#include "../src/function_Schematics_basetype_to_primitive.cpp"


class TestIntegerListType : public ::testing::Test {
protected:
    IntegerListType field;
};

TEST_F(TestIntegerListType, test_valid_integer_list) {
    std::string input_value = "[1, 2, 3]";
    EXPECT_EQ(field.to_primitive(input_value), "[1,2,3]"); 
}

TEST_F(TestIntegerListType, test_invalid_non_list_input) {
    EXPECT_THROW({
        try {
            field.to_primitive("not_a_list");
        }
        catch (const std::invalid_argument& e) {
            EXPECT_TRUE(std::string(e.what()).find("Value must be a string representing a list.") != std::string::npos);
            throw;
        }
    }, std::invalid_argument);
}

TEST_F(TestIntegerListType, test_empty_list) {
    std::string input_value = "[]";
    EXPECT_EQ(field.to_primitive(input_value), "[]");
}

TEST_F(TestIntegerListType, test_single_integer) {
    std::string input_value = "[5]";
    EXPECT_EQ(field.to_primitive(input_value), "[5]");
}
TEST_F(TestIntegerListType, test_empty_list_string) {
    std::string input_value = "[]";
    EXPECT_EQ(field.to_primitive(input_value), "[]");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}