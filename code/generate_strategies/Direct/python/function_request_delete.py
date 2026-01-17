import requests

class RequestUtils:
    def handle_delete_request(self, url: str) -> str:
        try:
            response = requests.delete(url, timeout=5, verify=False)
        except requests.exceptions.Timeout:
            return "timeout"
        except requests.exceptions.SSLError:
            return "ssl_error"
        except requests.exceptions.RequestException as e:
            if e.response is not None and e.response.status_code == 404:
                return "error 404"
            return f"error {e.response.status_code}" if e.response else "error"

        if response.status_code == 200 or response.status_code == 204:
            return "success"
        else:
            return f"error {response.status_code}"
