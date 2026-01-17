#include <gtest/gtest.h>
#include "../src/function_Schematics_model_to_native.cpp"

class TestPersonModel : public ::testing::Test {
protected:
    Person person;
};

TEST_F(TestPersonModel, test_valid_model) {
    std::string data = "John Doe,30";
    std::string expected = "John Doe 30 Unknown";
    EXPECT_EQ(person.get_native_representation(data), expected);
}

TEST_F(TestPersonModel, test_missing_name) {
    std::string data = "25";  // 只有一个数字，没有逗号
    std::string result = person.get_native_representation(data);
    EXPECT_EQ(result, "error: Invalid input format");
}

TEST_F(TestPersonModel, test_negative_age) {
    std::string data = "John Doe,-5";
    std::string result = person.get_native_representation(data);
    EXPECT_TRUE(result.find("error") != std::string::npos);
}

TEST_F(TestPersonModel, test_default_city) {
    std::string data = "Jane Smith,22";
    std::string result = person.get_native_representation(data);
    EXPECT_TRUE(result.find("Unknown") != std::string::npos);
}

TEST_F(TestPersonModel, test_all_fields_provided) {
    std::string data = "Alice,40";
    std::string expected = "Alice 40 Unknown";
    EXPECT_EQ(person.get_native_representation(data), expected);
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}