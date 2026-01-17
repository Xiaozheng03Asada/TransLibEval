import unittest
from function_shutil_rmtree import DirectoryDeleter
import os

class TestShutilRmtree(unittest.TestCase):

    def setUp(self):
        self.deleter = DirectoryDeleter()
        self.test_dir = "test_directory"
        self.create_test_directory(self.test_dir)

    def tearDown(self):
        
        if os.path.exists(self.test_dir):
            self.cleanup(self.test_dir)
        else:
            print(f"Directory '{self.test_dir}' does not exist, skipping cleanup.")

    def create_test_directory(self, directory_path: str):
        
        os.makedirs(directory_path, exist_ok=True)

        with open(f"{directory_path}/file1.txt", 'w') as f:
            f.write("This is a test file.")
        sub_dir = f"{directory_path}/subdir"
        os.makedirs(sub_dir, exist_ok=True)
        with open(f"{sub_dir}/file2.txt", 'w') as f:
            f.write("This is another test file.")

    def cleanup(self, directory_path: str):
        
        result = self.deleter.delete_directory_tree(directory_path)
        if "deleted" not in result:
            raise Exception(f"Failed to clean up directory: {result}")

    def test_delete_existing_directory(self):
        result = self.deleter.delete_directory_tree(self.test_dir)
        self.assertTrue(result.startswith(f"Directory '{self.test_dir}' deleted successfully."))

    def test_delete_non_existent_directory(self):
        non_existent_dir = "non_existent_directory"
        result = self.deleter.delete_directory_tree(non_existent_dir)
        self.assertEqual(result, f"Directory '{non_existent_dir}' does not exist.")

    def test_delete_directory_with_files(self):
        result = self.deleter.delete_directory_tree(self.test_dir)
        self.assertTrue(result.startswith(f"Directory '{self.test_dir}' deleted successfully."))

    def test_delete_with_permissions_error(self):
        protected_dir = f"{self.test_dir}/protected"
        os.makedirs(protected_dir, exist_ok=True)
        protected_file = f"{protected_dir}/protected_file.txt"
        with open(protected_file, 'w') as f:
            f.write("Protected content.")
        os.chmod(protected_file, 0o444)

        result = self.deleter.delete_directory_tree(protected_dir)
        os.chmod(protected_file, 0o666)

        self.assertTrue("Failed to delete directory" in result)

    def test_partial_deletion(self):
        try:
            protected_file = f"{self.test_dir}/protected_file.txt"
            with open(protected_file, 'w') as f:
                f.write("Partial deletion test.")
            
            os.chmod(protected_file, 0o444)

            result = self.deleter.delete_directory_tree(self.test_dir)
        except Exception as e:
            result = str(e)
        finally:
            os.chmod(protected_file, 0o666)
            if os.path.exists(self.test_dir):
                self.deleter.delete_directory_tree(self.test_dir)

        self.assertTrue("Failed to delete directory" in result or "Permission denied" in result)

if __name__ == '__main__':
    unittest.main()
