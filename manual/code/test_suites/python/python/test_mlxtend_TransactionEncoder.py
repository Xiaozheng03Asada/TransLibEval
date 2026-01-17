import unittest
from function_mlxtend_TransactionEncoder import TransactionEncoderWrapper

class TestTransactionEncoder(unittest.TestCase):

    def test_single_transaction(self):
        encoder = TransactionEncoderWrapper()
        transactions = "apple,banana"
        expected_output = "1,1"
        self.assertEqual(encoder.encode_transactions(transactions), expected_output)

    def test_multiple_transactions(self):
        encoder = TransactionEncoderWrapper()
        transactions = "apple,banana;banana,cherry;apple,cherry"
        expected_output = "1,1,0;0,1,1;1,0,1"
        self.assertEqual(encoder.encode_transactions(transactions), expected_output)

    def test_empty_transaction(self):
        encoder = TransactionEncoderWrapper()
        transactions = ""
        expected_output = "[]"
        self.assertEqual(encoder.encode_transactions(transactions), expected_output)

    def test_repeated_items(self):
        encoder = TransactionEncoderWrapper()
        transactions = "apple,banana,apple"
        expected_output = "1,1"
        self.assertEqual(encoder.encode_transactions(transactions), expected_output)

    def test_no_transactions(self):
        encoder = TransactionEncoderWrapper()
        transactions = ""
        expected_output = "[]"
        self.assertEqual(encoder.encode_transactions(transactions), expected_output)

if __name__ == '__main__':
    unittest.main()
