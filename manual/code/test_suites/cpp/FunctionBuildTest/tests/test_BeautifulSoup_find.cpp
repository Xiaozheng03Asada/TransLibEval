#include <gtest/gtest.h>
#include "../src/function_BeautifulSoup_find.cpp"

class TestHTMLParser : public ::testing::Test {
protected:
    HTMLParser parser;
};

TEST_F(TestHTMLParser, TestCase1) {
    std::string html = "<h1>Welcome to the site</h1>";
    EXPECT_EQ(parser.extract_first_h1_text(html), "Welcome to the site");
}

TEST_F(TestHTMLParser, TestCase2) {
    std::string html = R"(
        <h1>First Heading</h1>
        <h1>Second Heading</h1>
    )";
    EXPECT_EQ(parser.extract_first_h1_text(html), "First Heading");
}

TEST_F(TestHTMLParser, TestCase3) {
    std::string html = "<p>No h1 tag here</p>";
    EXPECT_EQ(parser.extract_first_h1_text(html), "None");
}

TEST_F(TestHTMLParser, TestCase4) {
    std::string html = "<h1>Single Heading</h1><p>Paragraph</p>";
    EXPECT_EQ(parser.extract_first_h1_text(html), "Single Heading");
}

TEST_F(TestHTMLParser, TestCase5) {
    std::string html = "";
    EXPECT_EQ(parser.extract_first_h1_text(html), "None");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}