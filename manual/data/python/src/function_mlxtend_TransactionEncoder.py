from mlxtend.preprocessing import TransactionEncoder

class TransactionEncoderWrapper:
    def encode_transactions(self, transactions: str) -> str:

        if not transactions:
            return "[]"

        transactions_list = [trans.split(",") for trans in transactions.split(";")]

        te = TransactionEncoder()
        te_ary = te.fit(transactions_list).transform(transactions_list)

        encoded_str = ";".join([",".join([str(int(val)) for val in row]) for row in te_ary])
        return encoded_str
