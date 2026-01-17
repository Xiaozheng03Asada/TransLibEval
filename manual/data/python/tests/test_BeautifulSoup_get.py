import unittest
from function_BeautifulSoup_get import HTMLParser

class TestHTMLParser(unittest.TestCase):
    
    def setUp(self):
        self.parser = HTMLParser()

    def test_case_1(self):
        html = '<a href="https://example.com">Example</a>'
        self.assertEqual(self.parser.extract_first_link(html), "https://example.com")

    def test_case_2(self):
        html = '''
        <a href="https://example1.com">Example1</a>
        <a href="https://example2.com">Example2</a>
        '''
        self.assertEqual(self.parser.extract_first_link(html), "https://example1.com")

    def test_case_3(self):
        html = '<p>No links here</p>'
        self.assertEqual(self.parser.extract_first_link(html), '')

    def test_case_4(self):
        html = '''
        <a href="https://example.com">Example</a>
        <div>Some content</div>
        <a href="https://example2.com">Example2</a>
        '''
        self.assertEqual(self.parser.extract_first_link(html), "https://example.com")

    def test_case_5(self):
        html = ''
        self.assertEqual(self.parser.extract_first_link(html), '')

if __name__ == '__main__':
    unittest.main()
