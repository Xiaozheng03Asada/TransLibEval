import unittest
from function_shutil_move import FileMover


class TestShutilMove(unittest.TestCase):

    def setUp(self):
        self.mover = FileMover()
        self.src_file = 'test_file.txt'
        self.src_dir = 'test_dir'
        self.dest_dir = 'dest_dir'

        self.mover.perform_task('create_file', f"{self.src_file},This is a test file.")
        self.mover.perform_task('create_directory', self.src_dir)
        self.mover.perform_task('create_file', f"{self.src_dir}/nested_file.txt,This is a file in a directory.")
        self.mover.perform_task('create_directory', self.dest_dir)

    def tearDown(self):
        for path in [self.src_file, self.src_dir, self.dest_dir]:
            if self.mover.perform_task('path_exists', path) == 'True':
                self.mover.perform_task('remove_path', path)

    def test_move_file(self):
        moved_path = self.mover.perform_task('move', f"{self.src_file},{self.dest_dir}")
        self.assertTrue(self.mover.perform_task('path_exists', f"{self.dest_dir}/test_file.txt") == 'True')
        self.assertFalse(self.mover.perform_task('path_exists', self.src_file) == 'True')

    def test_move_nonexistent_file(self):
        with self.assertRaises(FileNotFoundError):
            self.mover.perform_task('move', 'nonexistent.txt,dest_dir')

    def test_move_file_to_same_location(self):
        moved_path = self.mover.perform_task('move', f"{self.src_file},{self.src_file}")
        self.assertTrue(self.mover.perform_task('path_exists', self.src_file) == 'True')
        self.assertEqual(moved_path, self.src_file)

    def test_move_to_nonexistent_directory(self):
        non_existing_dest = 'non_existing_dest_dir'        
        if self.mover.perform_task('path_exists', non_existing_dest) == 'True':
            self.mover.perform_task('remove_path', non_existing_dest)
        moved_path = self.mover.perform_task('move', f"{self.src_dir},{non_existing_dest}")
        self.assertTrue(self.mover.perform_task('path_exists', non_existing_dest) == 'True')
        self.assertFalse(self.mover.perform_task('path_exists', self.src_dir) == 'True')
        
        self.mover.perform_task('remove_path', non_existing_dest)

    def test_move_large_file(self):
        large_file_path = f"{self.src_dir}/large_file.txt"
        large_content = b'\0' * 10**6  
        self.mover.perform_task('create_file', f"{large_file_path},{large_content.decode('latin1')}")
        self.mover.perform_task('move', f"{large_file_path},{self.dest_dir}")
        self.assertTrue(self.mover.perform_task('path_exists', f"{self.dest_dir}/large_file.txt") == 'True')
        self.assertFalse(self.mover.perform_task('path_exists', large_file_path) == 'True')


if __name__ == '__main__':
    unittest.main()
