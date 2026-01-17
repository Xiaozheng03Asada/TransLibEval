import asyncio

class AsyncTaskHandler:
    def run_async_task(self, name: str, delay: int) -> str:
        
        async def async_task(name: str, delay: int) -> str:
            await asyncio.sleep(delay)
            return f"Task {name} completed after {delay} seconds"

        return asyncio.run(async_task(name, delay))
