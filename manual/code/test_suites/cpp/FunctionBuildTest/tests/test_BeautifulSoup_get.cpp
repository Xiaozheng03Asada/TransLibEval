#include <gtest/gtest.h>
#include "../src/function_BeautifulSoup_get.cpp"

class TestHTMLParser : public ::testing::Test {
protected:
    HTMLParser parser;
};

TEST_F(TestHTMLParser, TestCase1) {
    std::string html = R"(<a href="https://example.com">Example</a>)";
    EXPECT_EQ(parser.extract_first_link(html), "https://example.com");
}

TEST_F(TestHTMLParser, TestCase2) {
    std::string html = R"(
        <a href="https://example1.com">Example1</a>
        <a href="https://example2.com">Example2</a>
    )";
    EXPECT_EQ(parser.extract_first_link(html), "https://example1.com");
}

TEST_F(TestHTMLParser, TestCase3) {
    std::string html = R"(<p>No links here</p>)";
    EXPECT_EQ(parser.extract_first_link(html), "");
}

TEST_F(TestHTMLParser, TestCase4) {
    std::string html = R"(
        <a href="https://example.com">Example</a>
        <div>Some content</div>
        <a href="https://example2.com">Example2</a>
    )";
    EXPECT_EQ(parser.extract_first_link(html), "https://example.com");
}

TEST_F(TestHTMLParser, TestCase5) {
    std::string html = "";
    EXPECT_EQ(parser.extract_first_link(html), "");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}