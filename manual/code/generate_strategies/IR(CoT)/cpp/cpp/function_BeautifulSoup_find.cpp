#include <string>
#include <libxml/HTMLparser.h>
#include <libxml/xpath.h>

class HTMLParser {
public:
    std::string extract_first_h1_text(const std::string& html_content) {
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

        std::string result = "None";
        xmlNodeSetPtr nodes = xpathObj->nodesetval;
        if (nodes && nodes->nodeNr > 0) {
            xmlChar* content = xmlNodeGetContent(nodes->nodeTab[0]);
            if (content) {
                result = reinterpret_cast<const char*>(content);
                xmlFree(content);
            }
        }

        xmlXPathFreeObject(xpathObj);
        xmlXPathFreeContext(xpathCtx);
        xmlFreeDoc(doc);

        return result;
    }
};