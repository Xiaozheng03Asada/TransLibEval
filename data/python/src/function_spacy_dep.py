import spacy


class DependencyParser:
    def test_dep(self, text: str) -> str:
        if not isinstance(text, str):
            raise TypeError("The input must be of string type.")

        nlp = spacy.load("en_core_web_sm")
        doc = nlp(text)

        dependencies = ""
        for token in doc:
            if token.dep_ != "punct":
                dependencies += f"{token.text} ({token.dep_}), "

        if dependencies:
            dependencies = dependencies[:-2]

        return dependencies
