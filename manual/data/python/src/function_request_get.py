import requests


class RequestHandler:

    def handle_request(self, url: str) -> str:
        try:
            response = requests.get(url, timeout=5, allow_redirects=False)
        except requests.exceptions.Timeout:
            return "timeout"

        if response.status_code == 200:
            try:
                if not response.content.strip():
                    return "non-json"
                response.json()
                return "success"
            except requests.exceptions.JSONDecodeError:
                return "non-json"
        elif 300 <= response.status_code < 400:
            return f"redirect {response.status_code}"
        else:
            return f"error {response.status_code}"
