import unittest
from function_request_head import RequestHandler

class TestHandleRequest(unittest.TestCase):

    def test_success(self):
        result = RequestHandler().handle_request('https://jsonplaceholder.typicode.com/posts/1')
        self.assertEqual(result, 'success')

    def test_redirect(self):
        result = RequestHandler().handle_request('https://httpstat.us/301')
        self.assertEqual(result, 'redirect 301')

    def test_error_403(self):
        result = RequestHandler().handle_request('https://httpstat.us/403')
        self.assertEqual(result, 'error 403')

    def test_error_500(self):
        result = RequestHandler().handle_request('https://httpstat.us/500')
        self.assertEqual(result, 'error 500')

    def test_timeout(self):
        result = RequestHandler().handle_request('https://10.255.255.1')
        self.assertEqual(result, 'timeout')

if __name__ == '__main__':
    unittest.main()
