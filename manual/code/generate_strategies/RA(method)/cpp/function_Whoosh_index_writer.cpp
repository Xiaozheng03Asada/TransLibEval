#include <string>
#include <xapian.h>
#include <filesystem>
#include <sstream>

class WhooshIndexManager {
public:
    template<typename T>
    std::string manage_and_search_documents(const std::string& index_dir, 
                                          const T& document_text = T(), 
                                          const T& search_query = T()) {
        try {
            if (!std::filesystem::exists(index_dir)) {
                std::filesystem::create_directories(index_dir);
            }

            if constexpr (std::is_same_v<T, std::string>) {
                if (!document_text.empty()) {
                    Xapian::WritableDatabase db(index_dir, Xapian::DB_CREATE_OR_OPEN);
                    Xapian::Document doc;
                    doc.set_data(document_text);

                    Xapian::TermGenerator indexer;
                    indexer.set_document(doc);
                    indexer.set_stemmer(Xapian::Stem("english"));
                    indexer.index_text(document_text);

                    db.add_document(doc);
                    db.commit();
                }

                if (!search_query.empty()) {
                    Xapian::Database db(index_dir);
                    Xapian::QueryParser qp;
                    qp.set_database(db);
                    qp.set_stemmer(Xapian::Stem("english"));
                    qp.set_stemming_strategy(Xapian::QueryParser::STEM_SOME);

                    Xapian::Query query = qp.parse_query(search_query);
                    Xapian::Enquire enquire(db);
                    enquire.set_query(query);

                    Xapian::MSet results = enquire.get_mset(0, 10);
                    if (results.empty()) {
                        return "No documents found";
                    }

                    std::ostringstream oss;
                    for (auto it = results.begin(); it != results.end(); ++it) {
                        if (it != results.begin()) {
                            oss << " ";
                        }
                        oss << it.get_document().get_data();
                    }
                    return oss.str();
                }
                return "No documents found";
            } else {
                return "No documents found";
            }
        } catch (const std::exception& e) {
            return std::string("Error: ") + e.what();
        }
    }
};