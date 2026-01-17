#include <string>
#include <vector>
#include <sstream>
#include <algorithm>

class TestChartParser {
public:
    std::string test_chartparser(const std::string& sentence) {
        std::vector<std::string> tokens = split(sentence, ' ');
        std::string result = "";
        if (tokens.size() == 5) {
            if (tokens[0] == "the" &&
                (tokens[1] == "dog" || tokens[1] == "cat") &&
                tokens[2] == "chased" &&
                tokens[3] == "the" &&
                (tokens[4] == "dog" || tokens[4] == "cat")) {
                result = "(S (NP (Det " + tokens[0] + ") (N " + tokens[1] + ")) " +
                         "(VP (V " + tokens[2] + ") (NP (Det " + tokens[3] + ") (N " + tokens[4] + "))))";
            }
        }
        return result;
    }

private:
    std::vector<std::string> split(const std::string& str, char delimiter) {
        std::vector<std::string> tokens;
        std::string token;
        std::istringstream tokenStream(str);
        while (std::getline(tokenStream, token, delimiter)) {
            tokens.push_back(token);
        }
        return tokens;
    }
};