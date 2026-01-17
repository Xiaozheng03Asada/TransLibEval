#include <string>
#include <vector>
#include <sstream>
#include <libxml/HTMLparser.h>
#include <libxml/xpath.h>

class HTMLParser {
public:
    std::string extract_paragraphs(const std::string& html_content) {
        if (html_content.empty()) {
            return "None";
        }

        // Parse the HTML content
        htmlDocPtr doc = htmlReadMemory(html_content.c_str(), html_content.size(), nullptr, nullptr, HTML_PARSE_NOERROR | HTML_PARSE_NOWARNING);
        if (!doc) {
            return "None";
        }

        xmlXPathContextPtr xpathCtx = xmlXPathNewContext(doc);
        if (!xpathCtx) {
            xmlFreeDoc(doc);
            return "None";
        }

        // Find all <p> tags
        xmlXPathObjectPtr xpathObj = xmlXPathEvalExpression(reinterpret_cast<const xmlChar*>("//p"), xpathCtx);
        if (!xpathObj) {
            xmlXPathFreeContext(xpathCtx);
            xmlFreeDoc(doc);
            return "None";
        }

        std::vector<std::string> paragraphs;
        xmlNodeSetPtr nodes = xpathObj->nodesetval;
        if (nodes) {
            for (int i = 0; i < nodes->nodeNr; ++i) {
                xmlChar* content = xmlNodeGetContent(nodes->nodeTab[i]);
                if (content) {
                    paragraphs.emplace_back(reinterpret_cast<const char*>(content));
                    xmlFree(content);
                }
            }
        }

        xmlXPathFreeObject(xpathObj);
        xmlXPathFreeContext(xpathCtx);
        xmlFreeDoc(doc);

        if (paragraphs.empty()) {
            return "None";
        }

        // Join paragraphs with "|"
        std::ostringstream result;
        for (size_t i = 0; i < paragraphs.size(); ++i) {
            if (i > 0) {
                result << "|";
            }
            result << paragraphs[i];
        }
        return result.str();
    }
};