import itertools


class CartesianProductProcessor:

    def test_product(self, input_string):
        if input_string is None:
            raise ValueError
        if not isinstance(input_string, str):
            raise ValueError
        try:
            lists = []
            current_list = []
            current_element = ""
            for char in input_string:
                if char == ',':
                    if current_element:
                        current_list.append(current_element)
                        current_element = ""
                elif char == ';':
                    if current_element:
                        current_list.append(current_element)
                        current_element = ""
                    if current_list:
                        lists.append(tuple(current_list))
                        current_list = []
                    else:
                        raise ValueError
                else:
                    current_element += char
            if current_element:
                current_list.append(current_element)
            if current_list:
                lists.append(tuple(current_list))
            if not lists:
                raise ValueError
            product_result = []
            for row in itertools.product(*lists):
                product_result.append(''.join(map(str, row)))
            return ';'.join(product_result)
        except:
            raise ValueError