import unittest
import os
import shutil
from function_Whoosh_searcher_search import WhooshSearcher

class TestWhooshSearch(unittest.TestCase):
    def setUp(self):
        self.index_dir = "test_search_index"
        self.searcher = WhooshSearcher()
        self.create_index(self.index_dir)

    def tearDown(self):
        if os.path.exists(self.index_dir):
            shutil.rmtree(self.index_dir)

    def create_index(self, index_dir: str):
        from whoosh.fields import Schema, TEXT
        from whoosh import index

        if not os.path.exists(index_dir):
            os.mkdir(index_dir)

        schema = Schema(content=TEXT(stored=True))
        ix = index.create_in(index_dir, schema)

        writer = ix.writer()
        writer.add_document(content="This is a test document.")
        writer.add_document(content="Another test document.")
        writer.add_document(content="Document with a different content.")
        writer.commit()

    def test_search_document(self):
        results = self.searcher.perform_search(self.index_dir, "test")
        self.assertEqual(results, "This is a test document., Another test document.")

    def test_search_no_results(self):
        results = self.searcher.perform_search(self.index_dir, "nonexistent")
        self.assertEqual(results, "No documents found")

    def test_search_multiple_matches(self):
        results = self.searcher.perform_search(self.index_dir, "document")
        self.assertEqual(results, "This is a test document., Another test document., Document with a different content.")

    def test_empty_query(self):
        results = self.searcher.perform_search(self.index_dir, "")
        self.assertEqual(results, "No documents found")

    def test_invalid_index_directory(self):
        with self.assertRaises(ValueError):
            self.searcher.perform_search("invalid_dir", "test")

if __name__ == '__main__':
    unittest.main()
