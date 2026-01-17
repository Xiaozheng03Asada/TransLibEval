import asyncio

class EventTaskHandler:
    @staticmethod
    def might_fail_function(event_status: str, task: str) -> str:
        async def wait_for_event():
            if event_status == "triggered":
                return f"Task {task} completed"
            return f"Task {task} is waiting for the event"

        result = asyncio.run(wait_for_event())
        return result
