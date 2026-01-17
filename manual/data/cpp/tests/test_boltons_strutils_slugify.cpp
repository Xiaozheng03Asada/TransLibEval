#include <gtest/gtest.h>
#include "../src/function_boltons_strutils_slugify.cpp"


TEST(TextSlugifierTest, TestSlugifyBasic) {
    TextSlugifier slugifier;
    std::string text = "Hello World";
    std::string result = slugifier.create_slug(text);
    std::string expected = "hello-world";
    EXPECT_EQ(result, expected);
}

TEST(TextSlugifierTest, TestSlugifyWithCustomDelimiter) {
    TextSlugifier slugifier;
    std::string text = "Python is awesome!";
    std::string result = slugifier.create_slug(text, "_");
    std::string expected = "python_is_awesome";
    EXPECT_EQ(result, expected);
}

TEST(TextSlugifierTest, TestSlugifySpecialCharacters) {
    TextSlugifier slugifier;
    std::string text = "Clean @!this** up$!";
    std::string result = slugifier.create_slug(text);
    std::string expected = "clean-this-up";
    EXPECT_EQ(result, expected);
}

TEST(TextSlugifierTest, TestSlugifyEmptyString) {
    TextSlugifier slugifier;
    std::string text = "";
    std::string result = slugifier.create_slug(text);
    std::string expected = "";
    EXPECT_EQ(result, expected);
}

TEST(TextSlugifierTest, TestSlugifyNonStringInput) {
    TextSlugifier slugifier;
    EXPECT_THROW(slugifier.create_slug(12345), std::invalid_argument);
}

int main(int argc, char **argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}