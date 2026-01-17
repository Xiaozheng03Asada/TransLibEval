import autopep8

class CodeFormatter:
    def format_code_with_custom_style(self, code: str) -> str:
        fixed_code = autopep8.fix_code(code, options={'select': ['E111', 'E701']})
        return fixed_code
