import spacy

class SpacyTextProcessor:
    def spacy_text(self, text: str) -> str:
        try:
            nlp = spacy.load('en_core_web_sm')
            doc = nlp(text)

            return " ".join([token.text for token in doc])
        except UnicodeDecodeError as e:
            return f"UnicodeDecodeError: {e}"
        except MemoryError:
            return "Insufficient memory error"
        except FileNotFoundError:
            return "Model file not found error"
        except ImportError:
            return "Error occurred while importing related modules or packages"
        except Exception as e:
            return f"Other errors: {e}"
