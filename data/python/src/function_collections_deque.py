from collections import deque


class DequeOperations:

    def perform_operation(self, operation_type):
        d = deque()

        if operation_type == 'append_and_appendleft':
            d.append(1)
            d.appendleft(0)
            return str(d[0]) + ", " + str(d[1])  # Return first two elements as string


        elif operation_type == 'pop_and_popleft':
            d = deque([1, 2, 3])
            d.popleft()
            d.pop()
            return str(len(d))  # Return length as string

        elif operation_type == 'remove':
            d = deque([1, 2, 3])
            d.remove(2)
            return str(d[0]) + ", " + str(d[1])  # Return first two elements as string

        elif operation_type == 'clear':
            d = deque([1, 2, 3])
            d.clear()
            return str(len(d))  # Return length as string

        else:
            raise ValueError("Invalid operation type")