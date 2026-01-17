#include <gtest/gtest.h>
#include "../src/function_pyfftw_fftn.cpp"  

TEST(TestFFTNFunctions, test_compute_fftn) {
    FFTNProcessor processor;
    std::string input_array = "[[1, 2, 3], [4, 5, 6], [7, 8, 9]]";
    std::string result = processor.compute_fftn(input_array);
    EXPECT_EQ(result, "(3, 3)");
}

TEST(TestFFTNFunctions, test_fftn_zero) {
    FFTNProcessor processor;
    std::string input_array = "[[0, 0, 0], [0, 0, 0], [0, 0, 0]]";
    std::string result = processor.compute_fftn(input_array);
    EXPECT_EQ(result, "(3, 3)");
}

TEST(TestFFTNFunctions, test_fftn_shape) {
    FFTNProcessor processor;
    std::string input_array = "[[1, 2], [3, 4]]";
    std::string result = processor.compute_fftn(input_array);
    EXPECT_EQ(result, "(2, 2)");
}

TEST(TestFFTNFunctions, test_fftn_complex_input) {
    FFTNProcessor processor;
    std::string input_array = "[[1+1j, 2+2j], [3+3j, 4+4j]]";
    std::string result = processor.compute_fftn(input_array);
    EXPECT_EQ(result, "(2, 2)");
}

TEST(TestFFTNFunctions, test_fftn_high_dimensional_large_input) {
    FFTNProcessor processor;
    std::string input_array = "[[[[0.1, 0.2], [0.3, 0.4]], [[0.5, 0.6], [0.7, 0.8]]]]";
    std::string result = processor.compute_fftn(input_array);
    EXPECT_EQ(result, "(1, 2, 2, 2)");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}