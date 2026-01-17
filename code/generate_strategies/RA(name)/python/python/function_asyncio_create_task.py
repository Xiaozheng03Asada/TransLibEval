import asyncio

class AsyncTaskHandler:
    def run_task(self, name: str, delay: int) -> str:
        
        if delay < 0:
            raise ValueError("Delay must be a non-negative integer")

        async def async_task(name: str, delay: int) -> str:
            await asyncio.sleep(delay)
            return f"Task {name} completed after {delay} seconds"

        loop = asyncio.new_event_loop()
        asyncio.set_event_loop(loop)
        try:
            result = loop.run_until_complete(async_task(name, delay))
        finally:
            loop.close()
        return result
