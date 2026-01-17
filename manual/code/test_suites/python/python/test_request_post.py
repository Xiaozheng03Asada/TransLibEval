import unittest
from function_request_post import PostRequestHandler

class TestHandlePostRequest(unittest.TestCase):

    def test_success_post(self):
        handler = PostRequestHandler()
        self.assertEqual(handler.handle_post_request('https://jsonplaceholder.typicode.com/posts', 'foo', 'bar', 1), 'success')

    def test_error_post(self):
        handler = PostRequestHandler()
        self.assertEqual(handler.handle_post_request('https://jsonplaceholder.typicode.com/invalid', 'foo', 'bar', 1), 'error 404')

    def test_timeout_post(self):
        handler = PostRequestHandler()
        self.assertEqual(handler.handle_post_request('https://10.255.255.1', 'foo', 'bar', 1), 'timeout')

    def test_large_data_post(self):
        large_data_title = "foo" * 10000
        large_data_body = "bar" * 10000
        handler = PostRequestHandler()
        self.assertEqual(handler.handle_post_request('https://jsonplaceholder.typicode.com/posts', large_data_title, large_data_body, 1), 'success')

    def test_post_with_empty_body(self):
        handler = PostRequestHandler()
        self.assertEqual(handler.handle_post_request('https://jsonplaceholder.typicode.com/posts', '', '', 1), 'success')

if __name__ == '__main__':
    unittest.main()
