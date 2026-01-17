#include <iostream>
#include <map>
#include <vector>
#include <algorithm>
#include <string>
#include <sstream>
#include <stanfordnlp/StanfordCoreNLP.h>
#include <stanfordnlp/CoreDocument.h>
#include <stanfordnlp/CoreSentence.h>
#include <stanfordnlp/SemanticGraph.h>
#include <stanfordnlp/TypedDependency.h>
#include <stanfordnlp/IndexedWord.h>

class function_spacy_dep {
public:
    std::string test_dep(const std::string& text) {
        class DependencyParser {
        private:
            StanfordCoreNLP pipeline;
            std::map<std::string, std::string> relationMapping;

        public:
            DependencyParser() {
                Properties props;
                props.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse");
                pipeline = StanfordCoreNLP(props);

                relationMapping = {
                    {"root", "ROOT"},
                    {"nsubj", "nsubj"},
                    {"obj", "dobj"},
                    {"det", "det"},
                    {"amod", "amod"},
                    {"obl", "pobj"},
                    {"case", "prep"}
                };
            }

            std::string parse(const std::string& text) {
                if (text.empty()) {
                    return "";
                }

                CoreDocument document(text);
                pipeline.annotate(document);

                CoreSentence sentence = document.sentences()[0];
                SemanticGraph dependencies = sentence.dependencyParse();

                std::map<int, std::string> orderedDeps;

                IndexedWord root = dependencies.getFirstRoot();
                if (root != nullptr) {
                    orderedDeps[root.index()] = root.word() + " (ROOT)";
                }

                std::vector<TypedDependency> typedDeps = dependencies.typedDependencies();
                std::sort(typedDeps.begin(), typedDeps.end(), [](const TypedDependency& d1, const TypedDependency& d2) {
                    return d1.dep().index() < d2.dep().index();
                });

                for (const TypedDependency& td : typedDeps) {
                    std::string reln = td.reln().getShortName();
                    std::transform(reln.begin(), reln.end(), reln.begin(), ::tolower);

                    if (reln != "root" && reln != "punct") {
                        std::string mappedReln = relationMapping.count(reln) ? relationMapping[reln] : reln;
                        IndexedWord dep = td.dep();
                        if (!orderedDeps.count(dep.index())) {
                            orderedDeps[dep.index()] = dep.word() + " (" + mappedReln + ")";
                        }
                    }
                }

                std::ostringstream result;
                for (const auto& dep : orderedDeps) {
                    result << dep.second << ", ";
                }

                std::string output = result.str();
                if (!output.empty()) {
                    output.pop_back();
                    output.pop_back();
                }

                return output;
            }
        };

        DependencyParser parser;
        return parser.parse(text);
    }
};