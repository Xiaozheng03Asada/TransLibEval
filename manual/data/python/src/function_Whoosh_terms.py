import os
from whoosh import index
from whoosh.fields import Schema, TEXT

class WhooshTerms:
    def manage_index_and_list_terms(self, index_dir: str, field_name: str = "content", add_documents: bool = True) -> str:
        if not os.path.exists(index_dir):
            if add_documents:
                os.mkdir(index_dir)
            else:
                raise ValueError(f"Index directory '{index_dir}' does not exist")
        
        if not index.exists_in(index_dir):
            if not add_documents:
                raise ValueError(f"No index found in directory '{index_dir}'")
            schema = Schema(content=TEXT(stored=True))
            ix = index.create_in(index_dir, schema)
            if add_documents:
                writer = ix.writer()
                writer.add_document(content="Hello world! This is a test document.")
                writer.add_document(content="Another test document is here.")
                writer.add_document(content="Whoosh makes text search easy.")
                writer.commit()
        else:
            ix = index.open_dir(index_dir)

        terms_list = []
        with ix.searcher() as searcher:
            field_terms = searcher.reader().iter_field(field_name)
            for term_bytes, term_info in field_terms:
                term = term_bytes.decode("utf-8")
                terms_list.append(term)

        ix.close()
        
        return ", ".join(terms_list)