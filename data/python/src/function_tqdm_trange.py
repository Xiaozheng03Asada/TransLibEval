from tqdm import trange

class ProgressRange:
    @staticmethod
    def might_fail_function(start: int, stop: int, step: int, desc: str = 'General trange loop', ascii: bool = True, miniters: int = None, maxinterval: float = None, mininterval: float = None) -> str:
        results = ""
        for i in trange(start, stop, step, desc=desc, ascii=ascii, miniters=miniters, maxinterval=maxinterval, mininterval=mininterval):
            result = str(i * 2)
            results += result
        return results
