from bs4 import BeautifulSoup

class HTMLParser:
    def extract_first_h1_text(self, html_content: str) -> str:
        soup = BeautifulSoup(html_content, 'html.parser')
        h1 = soup.find('h1')
        return h1.get_text() if h1 else "None"
