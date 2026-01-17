from patsy import Term

class PatsyTerm:
    def generate_and_convert_term(self, formula: str = 'x1 + x2 + x3') -> str:
        try:
            term = Term(formula)
        except Exception as e:
            raise ValueError(f"Error in generating Term from formula: {e}")
        
        return ' '.join(str(factor) for factor in term.factors).replace(" +", "+").replace(" *", "*").replace(" /", "/").replace(" -", "-")
