import nltk

class TestChartParser:

    def test_chartparser(self, sentence):
        grammar = nltk.CFG.fromstring()
        parser = nltk.ChartParser(grammar)
        
        parsed_trees = [str(tree) for tree in parser.parse(sentence.split())]
        if parsed_trees:
            return ' | '.join(parsed_trees)  
        else:
            return ''