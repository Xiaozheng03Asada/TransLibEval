#include <clang/Format/Format.h>
#include <clang/Tooling/Core/Replacement.h>
#include <clang/Tooling/ReplacementsYaml.h>
#include <string>

class CodeFormatter {
public:
    std::string format_code(const std::string& code) {
        
        clang::format::FormatStyle style = clang::format::getLLVMStyle();
        style.Language = clang::format::FormatStyle::LK_Cpp;
        
        
        clang::tooling::Replacements replacements;
        replacements = clang::format::reformat(style, code, 
            {clang::tooling::Range(0, code.size())}, "<input>");
        
        
        auto result = clang::tooling::applyAllReplacements(code, replacements);
        if (!result) {
            return code;
        }
        
        return *result;
    }
};