#include <string>
#include <sstream>
#include <type_traits>
#include <rapidjson/document.h>
#include <rapidjson/schema.h>
#include <rapidjson/stringbuffer.h>
#include <rapidjson/writer.h>


class NumberComparer {
public:
    
    template<typename T1, typename T2>
    std::string compare_numbers(const T1& num1, const T2& num2) {
        if constexpr (!std::is_arithmetic_v<T1>)
            return "Error: Invalid input. {'num1': ['must be of number type']}";
        else if constexpr (!std::is_arithmetic_v<T2>)
            return "Error: Invalid input. {'num2': ['must be of number type']}";
        else {
            
            const char* schemaJson = R"({
                "type": "object",
                "properties": {
                    "num1": { "type": "number" },
                    "num2": { "type": "number" }
                },
                "required": ["num1", "num2"]
            })";
            rapidjson::Document sd;
            sd.Parse(schemaJson);
            rapidjson::SchemaDocument schema(sd);

            
            rapidjson::Document d;
            d.SetObject();
            rapidjson::Document::AllocatorType& allocator = d.GetAllocator();
            d.AddMember("num1", rapidjson::Value(num1), allocator);
            d.AddMember("num2", rapidjson::Value(num2), allocator);

          
            rapidjson::SchemaValidator validator(schema);
            if (!d.Accept(validator)) {
               
                return std::string("Error: Invalid input. {") + validator.GetInvalidSchemaKeyword() + "}";
            }

            
            if (num1 > num2)
                return "Greater";
            else if (num1 < num2)
                return "Smaller";
            else
                return "Equal";
        }
    }
};