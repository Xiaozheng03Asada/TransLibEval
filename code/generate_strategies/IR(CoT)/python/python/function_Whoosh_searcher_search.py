from whoosh import index
from whoosh.qparser import QueryParser
from whoosh.index import EmptyIndexError
import os

class WhooshSearcher:
    def perform_search(self, index_dir: str, query_text: str) -> str:
        if not os.path.exists(index_dir):
            raise ValueError("Index directory does not exist.")

        try:
            ix = index.open_dir(index_dir)
        except EmptyIndexError:
            raise ValueError("Index is empty or invalid.")

        qp = QueryParser("content", ix.schema)
        query = qp.parse(query_text)

        with ix.searcher() as searcher:
            results = searcher.search(query)
            if results:
                return ', '.join([hit['content'] for hit in results])
            else:
                return "No documents found"
