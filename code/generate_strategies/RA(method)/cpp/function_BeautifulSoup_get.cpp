#include <string>
#include <libxml/HTMLparser.h>
#include <libxml/xpath.h>

class HTMLParser {
public:
    std::string extract_first_link(const std::string& html_content) {
        if (html_content.empty()) {
            return "";
        }

        // Parse the HTML content
        htmlDocPtr doc = htmlReadMemory(html_content.c_str(), html_content.size(), nullptr, nullptr, HTML_PARSE_NOERROR | HTML_PARSE_NOWARNING);
        if (!doc) {
            return "";
        }

        xmlXPathContextPtr xpathCtx = xmlXPathNewContext(doc);
        if (!xpathCtx) {
            xmlFreeDoc(doc);
            return "";
        }

        // Find the first <a> tag with an href attribute
        xmlXPathObjectPtr xpathObj = xmlXPathEvalExpression(reinterpret_cast<const xmlChar*>("//a[@href]"), xpathCtx);
        if (!xpathObj) {
            xmlXPathFreeContext(xpathCtx);
            xmlFreeDoc(doc);
            return "";
        }

        std::string result = "";
        xmlNodeSetPtr nodes = xpathObj->nodesetval;
        if (nodes && nodes->nodeNr > 0) {
            xmlChar* href = xmlGetProp(nodes->nodeTab[0], reinterpret_cast<const xmlChar*>("href"));
            if (href) {
                result = reinterpret_cast<const char*>(href);
                xmlFree(href);
            }
        }

        xmlXPathFreeObject(xpathObj);
        xmlXPathFreeContext(xpathCtx);
        xmlFreeDoc(doc);

        return result;
    }
};