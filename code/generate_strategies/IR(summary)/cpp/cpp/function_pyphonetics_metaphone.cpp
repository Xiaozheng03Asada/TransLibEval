#include <string>
#include <boost/algorithm/string.hpp>

class PhoneticProcessor
{
public:
    std::string generate_phonetics(const std::string &word)
    {
        if (word.empty())
        {
            return "The given string is empty.";
        }
        std::string result;
        std::string str = boost::to_upper_copy(word);
        char lastChar = '\0';
        bool skipNext = false;

        
        auto isVowel = [](char c) -> bool
        {
            return c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U';
        };

        for (size_t i = 0; i < str.length(); i++)
        {
            if (skipNext)
            {
                skipNext = false;
                continue;
            }

            char c = str[i];

            
            if (i + 3 < str.length())
            {
                std::string nextFour = str.substr(i, 4);
                if (nextFour == "TION" || nextFour == "SION")
                {
                    result.push_back('X');
                    result.push_back('N'); 
                    lastChar = 'N';
                    i += 3;
                    continue;
                }
            }

            
            if (i > 0 && c == lastChar && !isVowel(c) &&
                (c != 'N' || (i + 1 < str.length() && str[i + 1] != 'X')))
            {
                continue;
            }

            
            if (i == 0 && c == 'K' && i + 1 < str.length() && str[i + 1] == 'N')
            {
                i++;
                result.push_back('N');
                lastChar = 'N';
                continue;
            }

            switch (c)
            {
            case 'A':
            case 'E':
            case 'I':
            case 'O':
            case 'U':
                if (i == 0)
                {
                    result.push_back(c);
                    lastChar = c;
                }
                break;
            case 'B':
                result.push_back('B');
                lastChar = 'B';
                break;
            case 'C':
                if (i + 1 < str.length() &&
                    (str[i + 1] == 'I' || str[i + 1] == 'E' || str[i + 1] == 'Y'))
                {
                    result.push_back('S');
                    lastChar = 'S';
                }
                else
                {
                    result.push_back('K');
                    lastChar = 'K';
                }
                break;
            case 'D':
                result.push_back('T');
                lastChar = 'T';
                break;
            case 'F':
                result.push_back('F');
                lastChar = 'F';
                break;
            case 'G':
                result.push_back('K');
                lastChar = 'K';
                break;
            case 'H':
                if (i == 0 || !isVowel(str[i - 1]))
                {
                    result.push_back('H');
                    lastChar = 'H';
                }
                break;
            case 'J':
                result.push_back('J');
                lastChar = 'J';
                break;
            case 'K':
                if (i == 0 && i + 1 < str.length() && str[i + 1] == 'N')
                {
                    result.push_back('N');
                    lastChar = 'N';
                    i++;
                }
                else
                {
                    result.push_back('K');
                    lastChar = 'K';
                }
                break;
            case 'L':
                result.push_back('L');
                lastChar = 'L';
                break;
            case 'M':
                result.push_back('M');
                lastChar = 'M';
                break;
            case 'N':
                result.push_back('N');
                lastChar = 'N';
                if (i + 1 < str.length() && str[i + 1] == 'X')
                {
                    skipNext = false;
                }
                break;
            case 'P':
                if (i + 1 < str.length() && str[i + 1] == 'H')
                {
                    result.push_back('F');
                    lastChar = 'F';
                    i++;
                }
                else
                {
                    result.push_back('P');
                    lastChar = 'P';
                }
                break;
            case 'Q':
                result.push_back('K');
                lastChar = 'K';
                break;
            case 'R':
                result.push_back('R');
                lastChar = 'R';
                break;
            case 'S':
                result.push_back('S');
                lastChar = 'S';
                break;
            case 'T':
                result.push_back('T');
                lastChar = 'T';
                break;
            case 'V':
                result.push_back('F');
                lastChar = 'F';
                break;
            case 'W':
                if (i == 0)
                {
                    result.push_back('W');
                    lastChar = 'W';
                }
                break;
            case 'X':
                if (i > 0 && lastChar == 'N')
                    result.push_back('X');
                else
                    result.append("KS");
                lastChar = 'X';
                break;
            case 'Y':
                if (i == 0)
                {
                    result.push_back('Y');
                    lastChar = 'Y';
                }
                break;
            case 'Z':
                result.push_back('S');
                lastChar = 'S';
                break;
            default:
                break;
            }
        }

        if (!str.empty() && str.back() == 'N' && !result.empty() && result.back() != 'N')
        {
            result.push_back('N');
        }

        return result;
    }
};