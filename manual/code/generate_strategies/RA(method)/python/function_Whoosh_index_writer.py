from whoosh.index import create_in, open_dir
from whoosh.fields import Schema, TEXT
from whoosh.qparser import QueryParser
import os

class WhooshIndexManager:
    def manage_and_search_documents(self, index_dir: str, document_text: str = None, search_query: str = None) -> str:
        schema = Schema(content=TEXT(stored=True))
        
        if not os.path.exists(index_dir):
            try:
                os.makedirs(index_dir)
                create_in(index_dir, schema)
            except Exception as e:
                raise ValueError(f"Failed to initialize index directory: {index_dir}") from e

        ix = open_dir(index_dir)

        try:
            writer = ix.writer()
            if document_text:
                writer.add_document(content=document_text)
            writer.commit()
        except Exception as e:
            if 'writer' in locals() and not writer.is_closed:
                writer.cancel()
            raise e

        if search_query:
            with ix.searcher() as searcher:
                query = QueryParser("content", ix.schema).parse(search_query)
                results = [hit["content"] for hit in searcher.search(query)]
                return " ".join(results) if results else "No documents found"
        return "No documents found"
