import requests

class RequestHandler:
    def handle_request(self, url: str) -> str:
        try:
            response = requests.head(url, timeout=5, allow_redirects=False)
        except requests.exceptions.Timeout:
            return "timeout"

        if response.status_code == 200:
            return "success"
        elif 300 <= response.status_code < 400:
            return f"redirect {response.status_code}"
        else:
            return f"error {response.status_code}"
