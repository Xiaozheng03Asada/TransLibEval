#include <string>
#include <vector>
#include <sstream>
#include <boost/spirit/include/qi.hpp>
#include <boost/phoenix.hpp>

class TestChartParser {
public:
    std::vector<std::string> test_chartparser(const std::string& sentence) {
        namespace qi = boost::spirit::qi;
        namespace ascii = boost::spirit::ascii;

        std::vector<std::string> results;
        std::string::const_iterator iter = sentence.begin();
        std::string::const_iterator end = sentence.end();

        bool r = qi::phrase_parse(iter, end,
            (
                qi::lit("the") >> (qi::lit("dog") | qi::lit("cat")) >> 
                qi::lit("chased") >> qi::lit("the") >> (qi::lit("dog") | qi::lit("cat"))
            ),
            ascii::space
        );

        if (r && iter == end) {
            results.push_back("Parse successful");
        }

        return results;
    }
};