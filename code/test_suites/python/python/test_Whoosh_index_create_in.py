import os
import unittest
from function_Whoosh_index_create_in import IndexManager


class TestIndexManager(unittest.TestCase):
    def setUp(self):
        self.index_dir = "test_index"
        self.index_manager = IndexManager()

    def tearDown(self):
        if os.path.exists(self.index_dir):
            for root, dirs, files in os.walk(self.index_dir, topdown=False):
                for file in files:
                    os.remove(os.path.join(root, file))
                for dir in dirs:
                    os.rmdir(os.path.join(root, dir))
            os.rmdir(self.index_dir)

    def test_create_index(self):
        result = self.index_manager.add_and_search(self.index_dir)
        self.assertEqual(result, "No action performed")
        self.assertTrue(os.path.exists(self.index_dir))
        self.assertTrue(os.path.isdir(self.index_dir))

    def test_add_document(self):
        result = self.index_manager.add_and_search(self.index_dir, document_text="This is a test document.")
        self.assertEqual(result, "Document added successfully")
        result = self.index_manager.add_and_search(self.index_dir, query_text="test")
        self.assertEqual(result, "This is a test document.")

    def test_search_document(self):
        self.index_manager.add_and_search(self.index_dir, document_text="This is another test document.")
        self.index_manager.add_and_search(self.index_dir, document_text="This is a different document.")
        result = self.index_manager.add_and_search(self.index_dir, query_text="different")
        self.assertEqual(result, "This is a different document.")

    def test_multiple_documents(self):
        self.index_manager.add_and_search(self.index_dir, document_text="Document one")
        self.index_manager.add_and_search(self.index_dir, document_text="Document two")
        self.index_manager.add_and_search(self.index_dir, document_text="Document three")
        result = self.index_manager.add_and_search(self.index_dir, query_text="Document")
        self.assertEqual(result, "Document one Document two Document three")

    def test_search_nonexistent_keyword(self):
        self.index_manager.add_and_search(self.index_dir, document_text="Some real document content")
        result = self.index_manager.add_and_search(self.index_dir, query_text="nonexistent keyword")
        self.assertEqual(result, "")


if __name__ == "__main__":
    unittest.main()
