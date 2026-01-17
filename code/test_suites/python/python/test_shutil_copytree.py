import unittest
import os
from function_shutil_copytree import DirectoryHandler


class TestShutilCopyTree(unittest.TestCase):

    def setUp(self):
        self.handler = DirectoryHandler()
        self.src_dir = 'test_src_dir'
        self.dest_dir = 'test_dest_dir'

        os.makedirs(self.src_dir, exist_ok=True)
        self.file1 = os.path.join(self.src_dir, 'file1.txt')
        self.file2 = os.path.join(self.src_dir, 'file2.txt')
        os.makedirs(os.path.join(self.src_dir, 'subdir'), exist_ok=True)
        self.file3_in_subdir = os.path.join(self.src_dir, 'subdir', 'file3.txt')

        with open(self.file1, 'w') as f:
            f.write("This is file 1.")
        with open(self.file2, 'w') as f:
            f.write("This is file 2.")
        with open(self.file3_in_subdir, 'w') as f:
            f.write("This is file 3 in subdir.")

    def tearDown(self):
        if os.path.exists(self.src_dir):
            for root, _, files in os.walk(self.src_dir, topdown=False):
                for name in files:
                    os.remove(os.path.join(root, name))
                os.rmdir(root)
        if os.path.exists(self.dest_dir):
            for root, _, files in os.walk(self.dest_dir, topdown=False):
                for name in files:
                    os.remove(os.path.join(root, name))
                os.rmdir(root)

    def test_copy_directory(self):
        result = self.handler.handle_directory_operations(self.src_dir, self.dest_dir, operation="copy")
        self.assertIn(f"Directory copied from {self.src_dir} to {self.dest_dir}", result)

    def test_copy_directory_exists_error(self):
        os.makedirs(self.dest_dir, exist_ok=True)
        with self.assertRaises(FileExistsError):
            self.handler.handle_directory_operations(self.src_dir, self.dest_dir, operation="copy")

    def test_copy_directory_source_not_found(self):
        non_existing_dir = 'non_existing_dir'
        with self.assertRaises(FileNotFoundError):
            self.handler.handle_directory_operations(non_existing_dir, self.dest_dir, operation="copy")

    def test_copy_directory_with_permissions(self):
        result = self.handler.handle_directory_operations(self.src_dir, self.dest_dir, operation="copy")
        self.assertIn(f"Directory copied from {self.src_dir} to {self.dest_dir}", result)

    def test_copy_directory_with_hidden_files(self):
        hidden_file = os.path.join(self.src_dir, '.hidden_file.txt')
        with open(hidden_file, 'w') as f:
            f.write("This is a hidden file.")
        result = self.handler.handle_directory_operations(self.src_dir, self.dest_dir, operation="copy")
        self.assertIn(f"Directory copied from {self.src_dir} to {self.dest_dir}", result)


if __name__ == '__main__':
    unittest.main()
 