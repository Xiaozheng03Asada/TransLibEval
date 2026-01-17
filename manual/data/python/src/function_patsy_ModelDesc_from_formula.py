from patsy.desc import ModelDesc
import json

class PatsyParser:
    def parse_formula(self, formula: str) -> str:
        if not isinstance(formula, str):
            return "Error: Formula must be a string."
        try:
            model_desc = ModelDesc.from_formula(formula)
            return json.dumps({
                "lhs_terms": len(model_desc.lhs_termlist),
                "rhs_terms": len(model_desc.rhs_termlist)
            })
        except Exception:
            return "Error: Failed to parse formula."
