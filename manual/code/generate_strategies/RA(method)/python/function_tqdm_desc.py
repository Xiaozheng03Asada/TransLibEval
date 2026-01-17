from tqdm import tqdm

class ProgressBar:
    @staticmethod
    def process_with_progress_bar(data: str, desc_text: str = None) -> str:
        if desc_text and not isinstance(desc_text, str):
            raise TypeError("desc_text must be a string.")
        
        results = []
        for element in tqdm(data, desc=desc_text):
            processed_element = str(int(element) * 2)
            results.append(processed_element)
        return ''.join(results)
