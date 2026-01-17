from boltons.iterutils import chunked


class ChunkManager:
    def chunk_data(self, data_str, chunk_size):
        if not isinstance(data_str, str) or not isinstance(chunk_size, int):
            return "Error"
        data = data_str.split(",")
        try:
            result = chunked(data, chunk_size)
            return ";".join([",".join(chunk_item) for chunk_item in result])
        except Exception:
            return "Error"
