import autopep8

class AutoPep8Formatter:
    
    def format_code(self, code: str) -> str:
        return autopep8.fix_code(code)
