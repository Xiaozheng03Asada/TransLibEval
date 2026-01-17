from tqdm import tqdm

class ProgressBar:
    @staticmethod
    def might_fail_function(data: str):
        result = ""
        for element in tqdm(data):
            result += element
        return result
