import requests


class PostRequestHandler:

    def handle_post_request(self,url: str, title: str, body: str, userId: int) -> str:
        data = {"title": title, "body": body, "userId": userId}
        try:
            response = requests.post(url, json=data, timeout=5)
        except requests.exceptions.Timeout:
            return "timeout"

        if response.status_code == 200 or response.status_code == 201:
            return "success"
        else:
            return f"error {response.status_code}"
