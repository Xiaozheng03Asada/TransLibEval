from boltons.strutils import slugify

class TextSlugifier:
    def create_slug(self, text, delim='-'):
        if not isinstance(text, str):
            raise TypeError("Input text must be a string")
        return slugify(text, delim=delim)
