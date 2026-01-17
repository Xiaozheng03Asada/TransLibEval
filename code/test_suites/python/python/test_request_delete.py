import unittest
from function_request_delete import RequestUtils

class TestHandleDeleteRequest(unittest.TestCase):

    def test_success_delete(self):
        result = RequestUtils().handle_delete_request('https://jsonplaceholder.typicode.com/posts/1')
        self.assertEqual(result, 'success')

    def test_error_delete(self):
        result = RequestUtils().handle_delete_request('https://api.github.com/repos/nonexistentrepo')
        self.assertEqual(result, 'error 404')

    def test_timeout_delete(self):
        result = RequestUtils().handle_delete_request('https://10.255.255.1')
        self.assertEqual(result, 'timeout')

    def test_delete_nonexistent_user(self):
        result = RequestUtils().handle_delete_request('https://api.github.com/users/nonexistentuser')
        self.assertEqual(result, 'error 404')

    def test_delete_invalid_url(self):
        result = RequestUtils().handle_delete_request('https://invalid.url')
        self.assertEqual(result, 'ssl_error')

if __name__ == '__main__':
    unittest.main()
