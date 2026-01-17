from bs4 import BeautifulSoup

class HTMLParser:
    def extract_first_link(self, html_content: str) -> str:
        
        soup = BeautifulSoup(html_content, 'html.parser')
        first_link = soup.find('a')  
        return first_link.get('href', '') if first_link else ''  
