#include <string>
#include <vector>
#include <llvm/ADT/ArrayRef.h>
#include <clang/Format/Format.h>
#include <clang/Tooling/Core/Replacement.h>
#include <clang/Tooling/Tooling.h>

class ClangFormatter {
public:
    std::string format_code(const std::string& code) {
        clang::format::FormatStyle style = clang::format::getLLVMStyle();
        std::vector<clang::tooling::Range> ranges;
        ranges.emplace_back(0, code.size());
        
        auto replacements = clang::format::reformat(style, code, llvm::ArrayRef<clang::tooling::Range>(ranges));
        auto formattedExpected = clang::tooling::applyAllReplacements(code, replacements);
        if (!formattedExpected)
            return code;
        return formattedExpected.get();
    }
};