#include <gtest/gtest.h>
#include "../src/function_seaborn_set_theme.cpp"

class TestSeabornThemeSetter : public testing::Test {
protected:
    SeabornThemeSetter theme_setter;
};

TEST_F(TestSeabornThemeSetter, DefaultTheme) {
    std::string result = theme_setter.set_theme();
    EXPECT_EQ(result, "{'style': 'darkgrid', 'context': 'notebook', 'palette': 'deep'}");
}

TEST_F(TestSeabornThemeSetter, CustomTheme) {
    std::string result = theme_setter.set_theme("white", "talk", "muted");
    EXPECT_EQ(result, "{'style': 'white', 'context': 'talk', 'palette': 'muted'}");
}

TEST_F(TestSeabornThemeSetter, EmptyStyle) {
    std::string result = theme_setter.set_theme("");
    EXPECT_EQ(result, "{'style': 'darkgrid', 'context': 'notebook', 'palette': 'deep'}");
}

TEST_F(TestSeabornThemeSetter, EmptyContext) {
    std::string result = theme_setter.set_theme("darkgrid", "");
    EXPECT_EQ(result, "{'style': 'darkgrid', 'context': 'notebook', 'palette': 'deep'}");
}

TEST_F(TestSeabornThemeSetter, EmptyPalette) {
    std::string result = theme_setter.set_theme("darkgrid", "notebook", "");
    EXPECT_EQ(result, "{'style': 'darkgrid', 'context': 'notebook', 'palette': 'deep'}");
}

int main(int argc, char **argv) {
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}