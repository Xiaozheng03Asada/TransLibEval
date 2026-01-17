import unittest
from function_BeautifulSoup_find import HTMLParser

class TestHTMLParser(unittest.TestCase):

    def setUp(self):
        self.parser = HTMLParser()

    def test_case_1(self):
        html = '<h1>Welcome to the site</h1>'
        self.assertEqual(self.parser.extract_first_h1_text(html), "Welcome to the site")

    def test_case_2(self):
        html = '''
        <h1>First Heading</h1>
        <h1>Second Heading</h1>
        '''
        self.assertEqual(self.parser.extract_first_h1_text(html), "First Heading")

    def test_case_3(self):
        html = '<p>No h1 tag here</p>'
        self.assertEqual(self.parser.extract_first_h1_text(html), "None")

    def test_case_4(self):
        html = '<h1>Single Heading</h1><p>Paragraph</p>'
        self.assertEqual(self.parser.extract_first_h1_text(html), "Single Heading")

    def test_case_5(self):
        html = ''
        self.assertEqual(self.parser.extract_first_h1_text(html), "None")

if __name__ == '__main__':
    unittest.main()
