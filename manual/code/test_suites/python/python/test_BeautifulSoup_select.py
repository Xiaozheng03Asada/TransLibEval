import unittest
from function_BeautifulSoup_select import HTMLParser

class TestHTMLParser(unittest.TestCase):

    def setUp(self):
        self.parser = HTMLParser()

    def test_case_1(self):
        html = '<p>First paragraph.</p><p>Second paragraph.</p>'
        self.assertEqual(self.parser.extract_paragraphs(html), "First paragraph.|Second paragraph.")

    def test_case_2(self):
        html = '<p>Only one paragraph.</p>'
        self.assertEqual(self.parser.extract_paragraphs(html), "Only one paragraph.")

    def test_case_3(self):
        html = '<div>No paragraphs here</div>'
        self.assertEqual(self.parser.extract_paragraphs(html), "None")

    def test_case_4(self):
        html = '''
        <p>Paragraph 1</p>
        <div>Some content</div>
        <p>Paragraph 2</p>
        '''
        self.assertEqual(self.parser.extract_paragraphs(html), "Paragraph 1|Paragraph 2")

    def test_case_5(self):
        html = ''
        self.assertEqual(self.parser.extract_paragraphs(html), "None")

if __name__ == '__main__':
    unittest.main()
