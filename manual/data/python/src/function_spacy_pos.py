import spacy


class SpacyPosProcessor:
    def test_pos(self, text: str) -> str:
        if not isinstance(text, str):
            raise TypeError("The input must be of string type.")

        try:
            nlp = spacy.load('en_core_web_sm')
            doc = nlp(text)

            pos_tags = ""
            for token in doc:
                pos_tags += f"{token.text} ({token.pos_}), "

            if pos_tags:
                pos_tags = pos_tags[:-2]

            return pos_tags
        except Exception as e:
            print(f"OtherError: {e}")
            raise
