from mlxtend.preprocessing import TransactionEncoder

class OneHotEncode:
    def onehot_encode(self, transactions: str) -> str:
        te = TransactionEncoder()
        transactions = [transactions.replace(',', '').split()]
        te.fit(transactions)
        onehot = te.transform(transactions)
        return ', '.join(te.columns_)
