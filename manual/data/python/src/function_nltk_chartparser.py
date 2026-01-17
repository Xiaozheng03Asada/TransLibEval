import nltk

class TestChartParser:

    def test_chartparser(self, sentence):
        grammar = nltk.CFG.fromstring("""
            S -> NP VP
            NP -> Det N
            VP -> V NP
            Det -> 'the'
            N -> 'dog' | 'cat'
            V -> 'chased'
        """)
        parser = nltk.ChartParser(grammar)
        # 返回解析树的字符串形式（所有树合并为一个字符串，避免返回列表）
        parsed_trees = [str(tree) for tree in parser.parse(sentence.split())]
        if parsed_trees:
            return ' | '.join(parsed_trees)  # 用竖线分隔多个解析树
        else:
            return ''