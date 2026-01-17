#include <string>
#include <vector>
#include <boost/algorithm/string.hpp>
#include <boost/format.hpp>

class SeabornThemeSetter {
public:
    std::string set_theme(
        const std::string& style = "darkgrid",
        const std::string& context = "notebook",
        const std::string& palette = "deep") {
        
        std::vector<std::string> valid_styles = {
            "white", "dark", "whitegrid", "darkgrid", "ticks"
        };
        std::vector<std::string> valid_contexts = {
            "paper", "notebook", "talk", "poster"
        };
        std::vector<std::string> valid_palettes = {
            "deep", "muted", "bright", "pastel", "dark", "colorblind"
        };

        std::string final_style = style;
        std::string final_context = context;
        std::string final_palette = palette;

        if (std::find(valid_styles.begin(), valid_styles.end(), style) == valid_styles.end()) {
            final_style = "darkgrid";
        }
        if (std::find(valid_contexts.begin(), valid_contexts.end(), context) == valid_contexts.end()) {
            final_context = "notebook";
        }
        if (std::find(valid_palettes.begin(), valid_palettes.end(), palette) == valid_palettes.end()) {
            final_palette = "deep";
        }

        boost::format fmt("{'style': '%1%', 'context': '%2%', 'palette': '%3%'}");
        fmt % final_style % final_context % final_palette;
        
        return fmt.str();
    }
};