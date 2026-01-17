#include <gtest/gtest.h>
#include "../src/function_pyfftw_interfaces_numpy_fft_ifft.cpp"  

TEST(TestIFFTFunctions, test_ifft_simple_input) {
    IFFTProcessor processor;
    std::string input_array = "np.array([1 + 2j, 3 + 4j, 5 + 6j, 7 + 8j])";
    std::string result = processor.compute_ifft(input_array);
    EXPECT_EQ(result, "(4,)");
}

TEST(TestIFFTFunctions, test_ifft_zero_input) {
    IFFTProcessor processor;
    std::string input_array = "np.zeros(4, dtype=complex)";
    std::string result = processor.compute_ifft(input_array);
    EXPECT_EQ(result, "(4,)");
}

TEST(TestIFFTFunctions, test_ifft_invalid_input_real) {
    IFFTProcessor processor;
    std::string input_array = "np.array([1.0, 2.0, 3.0, 4.0])";
    EXPECT_THROW(processor.compute_ifft(input_array), std::invalid_argument);
}

TEST(TestIFFTFunctions, test_ifft_invalid_input_string) {
    IFFTProcessor processor;
    std::string input_array = "np.array([\"a\", \"b\", \"c\", \"d\"])";
    EXPECT_THROW(processor.compute_ifft(input_array), std::invalid_argument);
}

TEST(TestIFFTFunctions, test_ifft_large_input) {
    IFFTProcessor processor;
    std::string input_array = "np.random.random(1000) + 1j * np.random.random(1000)";
    std::string result = processor.compute_ifft(input_array);
    EXPECT_EQ(result, "(1000,)");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}
