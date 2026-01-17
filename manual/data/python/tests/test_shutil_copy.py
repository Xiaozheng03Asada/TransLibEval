import unittest
from function_shutil_copy import FileHandler


class TestShutilCopy(unittest.TestCase):

    def setUp(self):
        self.handler = FileHandler()
        self.src_file, self.dst_file = self.handler.handle_file_operations(operation="copy")

    def tearDown(self):
        self.handler.handle_file_operations(content=self.src_file, operation="remove")
        self.handler.handle_file_operations(content=self.dst_file, operation="remove")

    def test_copy_file_to_directory(self):
        self.assertEqual(self.handler.handle_file_operations(content=self.dst_file, operation="exists"), "True")

    def test_copy_file_content(self):
        src_content = self.handler.handle_file_operations(content=self.src_file, operation="read")
        dst_content = self.handler.handle_file_operations(content=self.dst_file, operation="read")
        self.assertEqual(src_content, dst_content)

    def test_copy_file_overwrite_existing_file(self):
        new_content = "New content"
        self.handler.handle_file_operations(content=new_content, operation="copy", target_path=self.dst_file)
        updated_content = self.handler.handle_file_operations(content=self.dst_file, operation="read")
        self.assertEqual(updated_content, new_content)

    def test_copy_file_to_current_directory(self):
        current_dir_file = "test_copy.txt"
        _, dst_file = self.handler.handle_file_operations(operation="copy", target_path=current_dir_file)
        self.assertEqual(self.handler.handle_file_operations(content=dst_file, operation="exists"), "True")

    def test_copy_non_existent_file(self):
        non_existent_file = "non_existent.txt"
        self.assertEqual(self.handler.handle_file_operations(content=non_existent_file, operation="exists"), "False")


if __name__ == '__main__':
    unittest.main()
