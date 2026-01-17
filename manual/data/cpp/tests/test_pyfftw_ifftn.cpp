#include <gtest/gtest.h>

#include "../src/function_pyfftw_ifftn.cpp"  

TEST(TestIFFTNFunctions, test_ifftn_high_dimensional_input) {
    IFFTNProcessor processor;
    std::string input_array = "np.random.random((3, 3, 3, 3))";
    std::string result = processor.compute_ifftn(input_array);
    EXPECT_EQ(result, "(3, 3, 3, 3)");
}

TEST(TestIFFTNFunctions, test_ifftn_high_dimensional_large_input) {
    IFFTNProcessor processor;
    std::string input_array = "np.random.random((2, 2, 2, 2, 2))";
    std::string result = processor.compute_ifftn(input_array);
    EXPECT_EQ(result, "(2, 2, 2, 2, 2)");
}

TEST(TestIFFTNFunctions, test_ifftn_high_dimensional_large_values) {
    IFFTNProcessor processor;
    std::string input_array = "np.ones((4, 4, 4, 4), dtype=float) * 1e10";
    std::string result = processor.compute_ifftn(input_array);
    EXPECT_EQ(result, "(4, 4, 4, 4)");
}

TEST(TestIFFTNFunctions, test_ifftn_empty_input) {
    IFFTNProcessor processor;
    std::string input_array = "np.array([])";
    EXPECT_THROW(processor.compute_ifftn(input_array), std::invalid_argument);
}

TEST(TestIFFTNFunctions, test_ifftn_large_value) {
    IFFTNProcessor processor;
    std::string input_array = "np.ones((4, 4, 4, 4), dtype=float) * 1e10";
    std::string result = processor.compute_ifftn(input_array);
    EXPECT_EQ(result, "(4, 4, 4, 4)");
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}