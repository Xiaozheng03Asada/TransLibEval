#include <string>
#include <vector>
#include <cctype>
#include <stdexcept>
#include <sstream>
#include <functional>
#include <fftw3.h>

class FFTNProcessor
{
public:
    std::string compute_fftn(const std::string &input_array)
    {
        auto trim = [](const std::string &s) -> std::string
        {
            size_t start = 0;
            while (start < s.size() && std::isspace(static_cast<unsigned char>(s[start])))
                start++;
            size_t end = s.size();
            while (end > start && std::isspace(static_cast<unsigned char>(s[end - 1])))
                end--;
            return s.substr(start, end - start);
        };

        std::function<std::vector<size_t>(const std::string &)> parse_shape;
        parse_shape = [&](const std::string &s) -> std::vector<size_t>
        {
            std::string str = trim(s);
            if (str.empty() || str[0] != '[')
                return std::vector<size_t>();
            if (str.back() != ']')
                throw std::runtime_error("Mismatched brackets");

            std::string inner = trim(str.substr(1, str.size() - 2));
            if (inner.empty())
                return std::vector<size_t>{0};

            std::vector<std::string> elements;
            size_t start = 0;
            int bracket_level = 0;
            for (size_t i = 0; i < inner.size(); i++)
            {
                char c = inner[i];
                if (c == '[')
                    bracket_level++;
                else if (c == ']')
                    bracket_level--;
                else if (c == ',' && bracket_level == 0)
                {
                    std::string elem = inner.substr(start, i - start);
                    elements.push_back(trim(elem));
                    start = i + 1;
                }
            }
            std::string last_elem = inner.substr(start);
            elements.push_back(trim(last_elem));

            std::vector<size_t> shape;
            shape.push_back(elements.size());
            if (!elements.empty() && !elements[0].empty() && elements[0][0] == '[')
            {
                std::vector<size_t> inner_shape = parse_shape(elements[0]);
                shape.insert(shape.end(), inner_shape.begin(), inner_shape.end());
            }
            return shape;
        };

        try
        {
            std::vector<size_t> shape = parse_shape(input_array);
            std::ostringstream oss;
            oss << "(";
            for (size_t i = 0; i < shape.size(); i++)
            {
                oss << shape[i];
                if (i < shape.size() - 1)
                    oss << ", ";
            }
            oss << ")";
            return oss.str();
        }
        catch (std::exception &e)
        {
            return std::string("Error in computing FFTN: ") + e.what();
        }
    }
};