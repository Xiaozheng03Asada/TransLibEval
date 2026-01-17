import autopep8

class CodeFormatter:
    def format_code(self, code: str) -> str:
        formatted_code = autopep8.fix_code(code)
        return formatted_code
