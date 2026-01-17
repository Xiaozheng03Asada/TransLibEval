#include <gtest/gtest.h>

#include "../src/function_pyfftw_interfaces_numpy_fft_rfft.cpp"  

TEST(TestRFFTFunctions, test_rfft_simple_array) {
    RFFTProcessor processor;
    std::string input_array = "np.array([1.0, 2.0, 3.0, 4.0])";
    std::string result = processor.compute_rfft(input_array);
    EXPECT_EQ(result, "(3,)");
}

TEST(TestRFFTFunctions, test_rfft_zeros) {
    RFFTProcessor processor;
    std::string input_array = "np.zeros(8)";
    std::string result = processor.compute_rfft(input_array);
    EXPECT_EQ(result, "(5,)");
}

TEST(TestRFFTFunctions, test_rfft_large_input) {
    RFFTProcessor processor;
    std::string input_array = "np.random.random(1024)";
    std::string result = processor.compute_rfft(input_array);
    EXPECT_EQ(result, "(513,)");
}

TEST(TestRFFTFunctions, test_rfft_invalid_input) {
    RFFTProcessor processor;
    std::string input_array = "np.array([\"a\", \"b\", \"c\", \"d\"])";
    EXPECT_THROW(processor.compute_rfft(input_array), std::invalid_argument);
}

TEST(TestRFFTFunctions, test_rfft_empty_input) {
    RFFTProcessor processor;
    std::string input_array = "np.array([])";
    EXPECT_THROW(processor.compute_rfft(input_array), std::invalid_argument);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}