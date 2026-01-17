from bs4 import BeautifulSoup

class HTMLParser:
    def extract_paragraphs(self, html_content: str) -> str:
        soup = BeautifulSoup(html_content, 'html.parser')
        paragraphs = [p.get_text() for p in soup.select('p')]
        return "|".join(paragraphs) if paragraphs else "None"
