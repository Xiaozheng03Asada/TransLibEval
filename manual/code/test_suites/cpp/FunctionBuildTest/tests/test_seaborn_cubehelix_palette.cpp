#include <gtest/gtest.h>
#include "../src/function_seaborn_cubehelix_palette.cpp"


class TestSeabornCubehelixPalette : public testing::Test {
protected:
    SeabornCubehelixPalette palette_generator;
};

TEST_F(TestSeabornCubehelixPalette, DefaultCubehelix) {
    std::string result = palette_generator.generate_cubehelix();
    std::regex color_pattern("\\([0-9.]+, [0-9.]+, [0-9.]+\\)");
    auto begin = std::sregex_iterator(result.begin(), result.end(), color_pattern);
    auto end = std::sregex_iterator();
    EXPECT_EQ(std::distance(begin, end), 6);
}

TEST_F(TestSeabornCubehelixPalette, CustomStartRotation) {
    std::string result = palette_generator.generate_cubehelix(4, 1.0, 0.5);
    std::regex color_pattern("\\([0-9.]+, [0-9.]+, [0-9.]+\\)");
    auto begin = std::sregex_iterator(result.begin(), result.end(), color_pattern);
    auto end = std::sregex_iterator();
    EXPECT_EQ(std::distance(begin, end), 4);
}

TEST_F(TestSeabornCubehelixPalette, GammaCorrection) {
    std::string result = palette_generator.generate_cubehelix(3, 0.5, -1.5, 2.0);
    std::regex color_pattern("\\([0-9.]+, [0-9.]+, [0-9.]+\\)");
    auto begin = std::sregex_iterator(result.begin(), result.end(), color_pattern);
    auto end = std::sregex_iterator();
    EXPECT_EQ(std::distance(begin, end), 3);
}

TEST_F(TestSeabornCubehelixPalette, LargePalette) {
    std::string result = palette_generator.generate_cubehelix(12);
    std::regex color_pattern("\\([0-9.]+, [0-9.]+, [0-9.]+\\)");
    auto begin = std::sregex_iterator(result.begin(), result.end(), color_pattern);
    auto end = std::sregex_iterator();
    EXPECT_EQ(std::distance(begin, end), 12);
}

TEST_F(TestSeabornCubehelixPalette, EmptyPalette) {
    std::string result = palette_generator.generate_cubehelix(0);
    EXPECT_EQ(result, "[]");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}