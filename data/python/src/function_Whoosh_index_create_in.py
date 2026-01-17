import os
from whoosh import index
from whoosh.fields import Schema, TEXT
from whoosh.qparser import QueryParser


class IndexManager:
    def add_and_search(self, index_dir: str, document_text: str = None, query_text: str = None) -> str:
        if not os.path.exists(index_dir):
            os.mkdir(index_dir)

        schema = Schema(content=TEXT(stored=True))
        if not index.exists_in(index_dir):
            self.ix = index.create_in(index_dir, schema)
        else:
            self.ix = index.open_dir(index_dir)

        if document_text:
            writer = self.ix.writer()
            writer.add_document(content=document_text)
            writer.commit()
            return "Document added successfully"

        if query_text:
            qp = QueryParser("content", self.ix.schema)
            query = qp.parse(query_text)
            with self.ix.searcher() as searcher:
                results = searcher.search(query)
                return " ".join([hit['content'] for hit in results])

        return "No action performed"
