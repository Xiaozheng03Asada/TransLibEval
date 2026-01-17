
#include <string>
#include <clang/Format/Format.h>
#include <clang/Tooling/Core/Replacement.h>
#include <string>

class CodeFormatter {
public:
    std::string format_code_with_custom_style(const std::string& code) {
        using namespace clang;
        
        format::FormatStyle style = format::getLLVMStyle();
        style.Language = format::FormatStyle::LK_Cpp;
        style.IndentWidth = 4;
        style.UseTab = format::FormatStyle::UT_Never;
        style.SpaceBeforeParens = format::FormatStyle::SBPO_Always;
        style.ColumnLimit = 79;
        
      
        tooling::Replacements replacements;
        replacements = format::reformat(style, code, {tooling::Range(0, code.size())}, "<input>");
        
        auto result = tooling::applyAllReplacements(code, replacements);
        if (!result) {
            return code;  
        }
        
        return *result;
    }
};
