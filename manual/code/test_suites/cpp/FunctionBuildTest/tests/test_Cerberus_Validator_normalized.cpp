#include <gtest/gtest.h>
#include "../src/function_Cerberus_Validator_normalized.cpp"

class TestNormalizedData : public ::testing::Test {
protected:
    void SetUp() override {
        normalizer = new DataNormalizer();
    }

    void TearDown() override {
        delete normalizer;
    }

    DataNormalizer* normalizer;
};

TEST_F(TestNormalizedData, DataWithMissingFields) {
    std::string data_str = R"({"name": "Alice"})";
    EXPECT_EQ(normalizer->normalize_data(data_str), R"({"name": "Alice", "age": 18})");
}

TEST_F(TestNormalizedData, DataWithAllFields) {
    std::string data_str = R"({"name": "Bob", "age": 25})";
    EXPECT_EQ(normalizer->normalize_data(data_str), R"({"name": "Bob", "age": 25})");
}

TEST_F(TestNormalizedData, DataWithMissingAge) {
    std::string data_str = R"({"name": "Charlie"})";
    EXPECT_EQ(normalizer->normalize_data(data_str), R"({"name": "Charlie", "age": 18})");
}

TEST_F(TestNormalizedData, EmptyData) {
    std::string data_str = "{}";
    EXPECT_EQ(normalizer->normalize_data(data_str), R"({"name": "Unknown", "age": 18})");
}

TEST_F(TestNormalizedData, InvalidInput) {
    EXPECT_EQ(normalizer->normalize_data(12345), "Error: Input must be a string");
}

int main(int argc, char** argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}