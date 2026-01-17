#include <string>
#include <xapian.h>
#include <filesystem>
#include <sstream>
#include <vector>
#include <stdexcept>


class WhooshTerms {
public:
    template<typename T>
    std::string manage_index_and_list_terms(const std::string& index_dir, const T& field_name = "content", bool add_documents = true) {
        try {
            if (!std::filesystem::exists(index_dir)) {
                if (add_documents) {
                    std::filesystem::create_directories(index_dir);
                } else {
                    throw std::runtime_error("Index directory does not exist.");
                }
            }

            Xapian::WritableDatabase db(index_dir, Xapian::DB_CREATE_OR_OPEN);

            if (add_documents) {
                Xapian::Document doc1, doc2, doc3;

                doc1.set_data("Hello world! This is a test document.");
                doc2.set_data("Another test document is here.");
                doc3.set_data("Whoosh makes text search easy.");

                Xapian::TermGenerator indexer;
                indexer.set_stemmer(Xapian::Stem("english"));

                indexer.set_document(doc1);
                indexer.index_text("Hello world! This is a test document.");
                db.add_document(doc1);

                indexer.set_document(doc2);
                indexer.index_text("Another test document is here.");
                db.add_document(doc2);

                indexer.set_document(doc3);
                indexer.index_text("Whoosh makes text search easy.");
                db.add_document(doc3);

                db.commit();
            }

            std::vector<std::string> terms_list;
            Xapian::Database read_db(index_dir);
            Xapian::TermIterator it = read_db.allterms_begin();
            for (; it != read_db.allterms_end(); ++it) {
                terms_list.push_back(*it);
            }

            std::ostringstream oss;
            for (size_t i = 0; i < terms_list.size(); ++i) {
                if (i > 0) {
                    oss << ", ";
                }
                oss << terms_list[i];
            }

            return oss.str();
        } catch (const std::exception& e) {
            throw std::runtime_error(std::string("Error: ") + e.what());
        }
    }
};