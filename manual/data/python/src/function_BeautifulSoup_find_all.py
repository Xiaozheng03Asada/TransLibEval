from bs4 import BeautifulSoup

class HTMLParser:
    def extract_links(self, html_content: str) -> str:
        soup = BeautifulSoup(html_content, 'html.parser')
        links = [a.get('href') for a in soup.find_all('a') if a.get('href')]
        return ",".join(links) if links else "None"
