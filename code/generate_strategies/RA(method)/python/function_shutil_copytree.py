import shutil
import os


class DirectoryHandler:
    def handle_directory_operations(self, src_dir: str, dest_dir: str, operation: str = "copy") -> str:
        
        if operation == "copy":
            if os.path.exists(dest_dir):
                raise FileExistsError(f"The destination directory '{dest_dir}' already exists.")
            shutil.copytree(src_dir, dest_dir)
            return f"Directory copied from {src_dir} to {dest_dir}."
        else:
            raise ValueError("Unsupported operation. Only 'copy' is supported.")
