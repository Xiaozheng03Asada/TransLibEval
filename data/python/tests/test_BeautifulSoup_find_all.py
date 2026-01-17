import unittest
from function_BeautifulSoup_find_all import HTMLParser

class TestHTMLParser(unittest.TestCase):

    def setUp(self):
        self.parser = HTMLParser()

    def test_case_1(self):
        html = '<a href="https://example.com">Example</a>'
        self.assertEqual(self.parser.extract_links(html), "https://example.com")

    def test_case_2(self):
        html = '''
        <a href="https://example1.com">Example1</a>
        <a href="https://example2.com">Example2</a>
        '''
        self.assertEqual(self.parser.extract_links(html), "https://example1.com,https://example2.com")

    def test_case_3(self):
        html = '<p>No links here</p>'
        self.assertEqual(self.parser.extract_links(html), "None")

    def test_case_4(self):
        html = '''
        <a href="https://example.com">Valid</a>
        <a>Missing href</a>
        '''
        self.assertEqual(self.parser.extract_links(html), "https://example.com")

    def test_case_5(self):
        html = ''
        self.assertEqual(self.parser.extract_links(html), "None")

if __name__ == '__main__':
    unittest.main()
