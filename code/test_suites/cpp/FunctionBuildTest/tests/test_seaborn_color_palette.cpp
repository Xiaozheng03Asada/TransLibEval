#include <gtest/gtest.h>
#include "../src/function_seaborn_color_palette.cpp"


class TestSeabornColorPalette : public testing::Test {
protected:
    SeabornColorPalette palette_generator;
};

TEST_F(TestSeabornColorPalette, DefaultPalette) {
    std::string result = palette_generator.generate_palette();
    // 使用正则表达式计算颜色元组的数量
    std::regex color_pattern("\\([0-9.]+, [0-9.]+, [0-9.]+\\)");
    auto begin = std::sregex_iterator(result.begin(), result.end(), color_pattern);
    auto end = std::sregex_iterator();
    EXPECT_EQ(std::distance(begin, end), 6);
}

TEST_F(TestSeabornColorPalette, BrightPalette) {
    std::string result = palette_generator.generate_palette("bright", 5);
    std::regex color_pattern("\\([0-9.]+, [0-9.]+, [0-9.]+\\)");
    auto begin = std::sregex_iterator(result.begin(), result.end(), color_pattern);
    auto end = std::sregex_iterator();
    EXPECT_EQ(std::distance(begin, end), 5);
}
TEST_F(TestSeabornColorPalette, ColorblindPalette) {
    std::string result = palette_generator.generate_palette("colorblind", 7);
    std::regex color_pattern("\\([0-9.]+, [0-9.]+, [0-9.]+\\)");
    auto begin = std::sregex_iterator(result.begin(), result.end(), color_pattern);
    auto end = std::sregex_iterator();
    EXPECT_EQ(std::distance(begin, end), 7);
    
    // 验证返回的字符串格式是否正确
    EXPECT_TRUE(result.front() == '[' && result.back() == ']');
}
TEST_F(TestSeabornColorPalette, EmptyPalette) {
    std::string result = palette_generator.generate_palette("deep", 0);
    EXPECT_EQ(result, "[]");
}

TEST_F(TestSeabornColorPalette, LargePalette) {
    std::string result = palette_generator.generate_palette("muted", 15);
    std::regex color_pattern("\\([0-9.]+, [0-9.]+, [0-9.]+\\)");
    auto begin = std::sregex_iterator(result.begin(), result.end(), color_pattern);
    auto end = std::sregex_iterator();
    EXPECT_EQ(std::distance(begin, end), 15);
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}