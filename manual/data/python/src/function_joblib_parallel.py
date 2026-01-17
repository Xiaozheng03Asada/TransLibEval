from joblib import Parallel, delayed

class ParallelProcessor:

    def run_parallel(self, data_1=None, data_2=None, data_3=None, data_4=None, data_5=None, data_6=None, data_7=None, data_8=None, data_9=None, data_10=None, n_jobs=1):
        def process_data(x):
            return x ** 2

        data_items = [data_1, data_2, data_3, data_4, data_5, data_6, data_7, data_8, data_9, data_10]
        results = Parallel(n_jobs=n_jobs)(delayed(process_data)(x) for x in data_items if x is not None)

        return ', '.join(map(str, results))
