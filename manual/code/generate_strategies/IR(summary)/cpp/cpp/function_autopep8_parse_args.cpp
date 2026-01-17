#include <string>
#include <CLI/CLI.hpp>
#include <sstream>
#include <vector>
#include <cstring>

class ArgumentParser {
public:
    std::string parse_arguments(const std::string& input_str) {
        CLI::App app{"Example command-line tool"};
        
        std::string name;
        int age;
        std::string city;
    
        app.add_option("-n,--name", name, "Your name")->required();
        app.add_option("-a,--age", age, "Your age")->required();
        app.add_option("-c,--city", city, "Your city");
    
        try {
            std::vector<std::string> args = {"program"};
            std::string current_token;
            bool in_quotes = false;
            
            
            for(size_t i = 0; i < input_str.length(); i++) {
                if(input_str[i] == '"') {
                    in_quotes = !in_quotes;
                    continue;
                }
                
                if(input_str[i] == ' ' && !in_quotes) {
                    if(!current_token.empty()) {
                        args.push_back(current_token);
                        current_token.clear();
                    }
                } else {
                    current_token += input_str[i];
                }
            }
            if(!current_token.empty()) {
                args.push_back(current_token);
            }
            
          
            std::vector<char*> argv_vec;
            for(auto& arg : args) {
                argv_vec.push_back(&arg[0]);
            }
            
        
            app.parse(argv_vec.size(), argv_vec.data());
            
            return "Name: " + name + ", Age: " + std::to_string(age) + 
                   ", City: " + (city.empty() ? "Not provided" : city);
        } 
        catch (const CLI::ParseError& e) {
            return "Error: 2";
        }
    }
};