#include <gtest/gtest.h>
#include "../src/function_spacy_vector.cpp"

class TestGetVectorLengths : public ::testing::Test {
protected:
    SpacyVectorProcessor processor;
};

TEST_F(TestGetVectorLengths, test_function_called) {
    std::string text = "hello world";
    std::string result = processor.vector_lengths(text);
    ASSERT_FALSE(result.empty());
}

TEST_F(TestGetVectorLengths, test_get_vector_lengths_length_matches_tokens) {
    std::string text = "hello world";
    double hello_vector_length = std::sqrt(1.0*1.0 + 2.0*2.0 + 3.0*3.0);
    double world_vector_length = std::sqrt(4.0*4.0 + 5.0*5.0 + 6.0*6.0);
    std::ostringstream oss;
    oss << "hello: " << std::fixed << std::setprecision(6) << hello_vector_length
        << ", world: " << std::fixed << std::setprecision(6) << world_vector_length;
    std::string expected_lengths = oss.str();

    std::string result = processor.vector_lengths(text);
    ASSERT_EQ(expected_lengths, result);
}

TEST_F(TestGetVectorLengths, test_vector_lengths_with_punctuation) {
    std::string text = "hello, world!";
    std::string lengths_str = processor.vector_lengths(text);
    std::vector<std::string> split_result;
    boost::split(split_result, lengths_str, boost::is_any_of(", "));

    // 移除空字符串
    split_result.erase(std::remove_if(split_result.begin(), split_result.end(), [](const std::string& s) { return s.empty(); }), split_result.end());

    ASSERT_EQ(split_result.size(), 5);
}


TEST_F(TestGetVectorLengths, test_vector_lengths_case_sensitivity) {
    std::string text1 = "Hello";
    std::string text2 = "hello";

    std::string lengths1_str = processor.vector_lengths(text1);
    std::string lengths2_str = processor.vector_lengths(text2);

    auto parse_length = [](const std::string& s) {
        size_t colon_pos = s.find(":");
        return std::stod(s.substr(colon_pos + 1));
    };

    double length1 = parse_length(lengths1_str);
    double length2 = parse_length(lengths2_str);

    ASSERT_NE(length1, length2);
}

TEST_F(TestGetVectorLengths, test_output_type) {
    std::string text = "hello world";
    std::string result = processor.vector_lengths(text);
    ASSERT_TRUE(!result.empty());
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}