from schema import Schema, And, Use

class PersonValidator:
    def validate(self, name, age, city):
        schema = Schema({
            'name': And(str, len),
            'age': And(Use(int), lambda n: 18 <= n <= 100),
            'city': And(str, lambda s: s.isalpha() or " " in s)
        })
        data = {'name': name, 'age': age, 'city': city}
        try:
            schema.validate(data)
            return f"Valid data: name = {name}, age = {age}, city = {city}"
        except:
            return "Invalid data: One or more fields are incorrect"
