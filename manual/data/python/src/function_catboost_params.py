from catboost import CatBoostClassifier


class CatBoostParamFetcher:
    def get_model_params(self, iterations: int, depth: int, learning_rate: float) -> str:
        model = CatBoostClassifier(iterations=iterations, depth=depth, learning_rate=learning_rate, silent=True)
        model_params = model.get_params()
        iterations = model_params["iterations"]
        depth = model_params["depth"]
        learning_rate = model_params["learning_rate"]

        return f"Model Params - Iterations: {iterations}, Depth: {depth}, Learning Rate: {learning_rate}"
