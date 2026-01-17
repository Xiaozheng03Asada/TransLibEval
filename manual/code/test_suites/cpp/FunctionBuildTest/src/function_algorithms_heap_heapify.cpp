#include <vector>
#include <string>
#include <sstream>
#include <algorithm>

class HeapifyFunction {
public:
    std::string heapify_from_string(const std::string& arr_str) {
        class MinHeap {
        private:
            std::vector<int> heap;
            int size;

            void heapify(int i) {
                int smallest = i;
                int left = 2 * i + 1;
                int right = 2 * i + 2;

                if (left < size && heap[left] < heap[smallest]) {
                    smallest = left;
                }

                if (right < size && heap[right] < heap[smallest]) {
                    smallest = right;
                }

                if (smallest != i) {
                    std::swap(heap[i], heap[smallest]);
                    heapify(smallest);
                }
            }

        public:
            MinHeap(const std::vector<int>& arr) : heap(arr), size(arr.size()) {
                for (int i = size / 2 - 1; i >= 0; i--) {
                    heapify(i);
                }
            }

            std::vector<int> getHeap() const {
                return heap;
            }
        };

        std::vector<int> arr;
        std::stringstream ss(arr_str);
        std::string item;
        while (std::getline(ss, item, ',')) {
            arr.push_back(std::stoi(item));
        }

        MinHeap minHeap(arr);
        std::vector<int> result = minHeap.getHeap();

        std::stringstream result_ss;
        for (size_t i = 0; i < result.size(); ++i) {
            result_ss << result[i];
            if (i < result.size() - 1) {
                result_ss << ",";
            }
        }

        return result_ss.str();
    }
};