#include <string>
#include <vector>
#include <sstream>
#include <libxml/HTMLparser.h>
#include <libxml/xpath.h>

class HTMLParser {
public:
    std::string extract_links(const std::string& html_content) {
        if (html_content.empty()) {
            return "None";
        }

        
        htmlDocPtr doc = htmlReadMemory(html_content.c_str(), html_content.size(), nullptr, nullptr, HTML_PARSE_NOERROR | HTML_PARSE_NOWARNING);
        if (!doc) {
            return "None";
        }

        xmlXPathContextPtr xpathCtx = xmlXPathNewContext(doc);
        if (!xpathCtx) {
            xmlFreeDoc(doc);
            return "None";
        }

        
        xmlXPathObjectPtr xpathObj = xmlXPathEvalExpression(reinterpret_cast<const xmlChar*>("
        if (!xpathObj) {
            xmlXPathFreeContext(xpathCtx);
            xmlFreeDoc(doc);
            return "None";
        }

        std::vector<std::string> links;
        xmlNodeSetPtr nodes = xpathObj->nodesetval;
        if (nodes) {
            for (int i = 0; i < nodes->nodeNr; ++i) {
                xmlChar* href = xmlGetProp(nodes->nodeTab[i], reinterpret_cast<const xmlChar*>("href"));
                if (href) {
                    links.emplace_back(reinterpret_cast<const char*>(href));
                    xmlFree(href);
                }
            }
        }

        xmlXPathFreeObject(xpathObj);
        xmlXPathFreeContext(xpathCtx);
        xmlFreeDoc(doc);

        if (links.empty()) {
            return "None";
        }

        
        std::ostringstream result;
        for (size_t i = 0; i < links.size(); ++i) {
            if (i > 0) {
                result << ",";
            }
            result << links[i];
        }
        return result.str();
    }
};