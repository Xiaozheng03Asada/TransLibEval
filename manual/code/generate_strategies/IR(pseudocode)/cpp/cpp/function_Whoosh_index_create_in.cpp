#include <string>
#include <xapian.h>
#include <filesystem>
#include <memory>
#include <sstream>

class IndexManager {
public:
    template<typename T>
    std::string add_and_search(const std::string& index_dir, 
                             const T& document_text = T(), 
                             const T& query_text = T()) {
        try {
            if (!std::filesystem::exists(index_dir)) {
                std::filesystem::create_directory(index_dir);
            }

            if constexpr (std::is_same_v<T, std::string>) {
                if (!document_text.empty()) {
                    try {
                        Xapian::WritableDatabase wdb(index_dir, Xapian::DB_CREATE_OR_OPEN);
                        Xapian::Document doc;
                        doc.set_data(document_text);
                        
                        Xapian::TermGenerator term_gen;
                        term_gen.set_document(doc);
                        term_gen.set_stemmer(Xapian::Stem("english"));
                        term_gen.set_stemming_strategy(Xapian::TermGenerator::STEM_SOME);
                        term_gen.index_text(document_text);
                        
                        wdb.add_document(doc);
                        wdb.commit();
                        return "Document added successfully";
                    } catch (const Xapian::Error& e) {
                        return std::string("Error adding document: ") + e.get_msg();
                    }
                }

                if (!query_text.empty()) {
                    try {
                        Xapian::Database db(index_dir);
                        Xapian::QueryParser qp;
                        qp.set_database(db);
                        qp.set_stemmer(Xapian::Stem("english"));
                        qp.set_stemming_strategy(Xapian::QueryParser::STEM_SOME);
                        
                        Xapian::Query query = qp.parse_query(query_text);
                        Xapian::Enquire enquire(db);
                        enquire.set_query(query);
                        
                        Xapian::MSet matches = enquire.get_mset(0, 10);
                        std::ostringstream oss;
                        
                        for (auto it = matches.begin(); it != matches.end(); ++it) {
                            if (it != matches.begin()) {
                                oss << " ";
                            }
                            oss << it.get_document().get_data();
                        }
                        return oss.str();
                    } catch (const Xapian::Error& e) {
                        return std::string("Error searching: ") + e.get_msg();
                    }
                }
            }
            
            return "No action performed";
        } catch (const std::exception& e) {
            return std::string("Error: ") + e.what();
        }
    }
};