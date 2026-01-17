#include <gtest/gtest.h>
#include "../src/function_BeautifulSoup_select.cpp"

class TestHTMLParser : public ::testing::Test {
protected:
    HTMLParser parser;
};

TEST_F(TestHTMLParser, TestCase1) {
    std::string html = "<p>First paragraph.</p><p>Second paragraph.</p>";
    EXPECT_EQ(parser.extract_paragraphs(html), "First paragraph.|Second paragraph.");
}

TEST_F(TestHTMLParser, TestCase2) {
    std::string html = "<p>Only one paragraph.</p>";
    EXPECT_EQ(parser.extract_paragraphs(html), "Only one paragraph.");
}

TEST_F(TestHTMLParser, TestCase3) {
    std::string html = "<div>No paragraphs here</div>";
    EXPECT_EQ(parser.extract_paragraphs(html), "None");
}

TEST_F(TestHTMLParser, TestCase4) {
    std::string html = R"(
        <p>Paragraph 1</p>
        <div>Some content</div>
        <p>Paragraph 2</p>
    )";
    EXPECT_EQ(parser.extract_paragraphs(html), "Paragraph 1|Paragraph 2");
}

TEST_F(TestHTMLParser, TestCase5) {
    std::string html = "";
    EXPECT_EQ(parser.extract_paragraphs(html), "None");
}

int main(int argc, char** argv) {
    ::testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}