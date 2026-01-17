import argparse

class ArgumentParser:
    def parse_arguments(self, input_str: str)->str:
        parser = argparse.ArgumentParser(description='Example command-line tool')

        parser.add_argument('-n', '--name', type=str, required=True, help='Your name')
        parser.add_argument('-a', '--age', type=int, required=True, help='Your age')
        parser.add_argument('-c', '--city', type=str, help='Your city')

        try:
            args = parser.parse_args(input_str.split())
            return f'Name: {args.name}, Age: {args.age}, City: {args.city if args.city else "Not provided"}'
        except SystemExit as e:
            return f'Error: {str(e)}'
