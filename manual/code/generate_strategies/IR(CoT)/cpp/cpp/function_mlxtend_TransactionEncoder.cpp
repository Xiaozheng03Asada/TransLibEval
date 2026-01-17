#include <string>
#include <vector>
#include <unordered_map>
#include <set>
#include <sstream>
#include <armadillo>

class TransactionEncoderWrapper {
public:
    std::string encode_transactions(const std::string& transactions) {
        if (transactions.empty()) {
            return "[]";
        }


        std::vector<std::set<std::string>> transactions_list;
        std::istringstream transactionStream(transactions);
        std::string transaction;

        while (std::getline(transactionStream, transaction, ';')) {
            std::set<std::string> items;
            std::istringstream itemStream(transaction);
            std::string item;
            while (std::getline(itemStream, item, ',')) {
                items.insert(item);
            }
            transactions_list.push_back(items);
        }


        std::set<std::string> unique_items;
        for (const auto& trans : transactions_list) {
            unique_items.insert(trans.begin(), trans.end());
        }


        std::unordered_map<std::string, int> item_to_index;
        int index = 0;
        for (const auto& item : unique_items) {
            item_to_index[item] = index++;
        }


        arma::Mat<int> encoded_matrix(transactions_list.size(), unique_items.size(), arma::fill::zeros);
        for (size_t i = 0; i < transactions_list.size(); ++i) {
            for (const auto& item : transactions_list[i]) {
                encoded_matrix(i, item_to_index[item]) = 1;
            }
        }


        std::ostringstream encoded_str;
        for (size_t i = 0; i < encoded_matrix.n_rows; ++i) {
            for (size_t j = 0; j < encoded_matrix.n_cols; ++j) {
                encoded_str << encoded_matrix(i, j);
                if (j != encoded_matrix.n_cols - 1) {
                    encoded_str << ",";
                }
            }
            if (i != encoded_matrix.n_rows - 1) {
                encoded_str << ";";
            }
        }

        return encoded_str.str();
    }
};