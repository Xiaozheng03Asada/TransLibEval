import os
import shutil
import unittest
from function_Whoosh_index_writer import WhooshIndexManager

class TestManageSearchDocuments(unittest.TestCase):
    def setUp(self):
        self.index_dir = "test_index"
        self.manager = WhooshIndexManager()
        self._initialize_index(self.index_dir)

    def tearDown(self):
        if os.path.exists(self.index_dir):
            shutil.rmtree(self.index_dir)

    def _initialize_index(self, index_dir):
        self.manager.manage_and_search_documents(index_dir, document_text="Document 1: Test text.")

    def test_add_document(self):
        result = self.manager.manage_and_search_documents(self.index_dir, document_text="Document 2: Another test text.", search_query="Document")
        self.assertIn("Document 2: Another test text.", result)

    def test_empty_index_search(self):
        result = self.manager.manage_and_search_documents(self.index_dir, search_query="Nonexistent")
        self.assertEqual(result, "No documents found")

    def test_empty_index(self):
        result = self.manager.manage_and_search_documents(self.index_dir, document_text="Document 2: Another test text.", search_query="Document")
        self.assertIn("Document 2: Another test text.", result)

    def test_multiple_updates_on_same_document(self):
        self.manager.manage_and_search_documents(self.index_dir, document_text="Updated Document: Final version.")
        result = self.manager.manage_and_search_documents(self.index_dir, search_query="Updated")
        self.assertIn("Updated Document: Final version.", result)

    def test_search_specific_document(self):
        result = self.manager.manage_and_search_documents(self.index_dir, search_query="Test")
        self.assertIn("Document 1: Test text.", result)

if __name__ == "__main__":
    unittest.main()
