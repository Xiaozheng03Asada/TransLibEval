import unittest
from function_request_get import RequestHandler


class TestHandleRequest(unittest.TestCase):

    def setUp(self):
        # Create an instance of RequestHandler for each test
        self.handler = RequestHandler()

    def test_success(self):
        result = self.handler.handle_request('https://jsonplaceholder.typicode.com/posts/1')
        self.assertEqual(result, 'success')

    def test_redirect(self):
        result = self.handler.handle_request('https://httpstat.us/301')
        self.assertEqual(result, 'redirect 301')

    def test_error_403(self):
        result = self.handler.handle_request('https://httpstat.us/403')
        self.assertEqual(result, 'error 403')

    def test_error_500(self):
        result = self.handler.handle_request('https://httpstat.us/500')
        self.assertEqual(result, 'error 500')

    def test_timeout(self):
        result = self.handler.handle_request('https://10.255.255.1')
        self.assertEqual(result, 'timeout')


if __name__ == '__main__':
    unittest.main()
