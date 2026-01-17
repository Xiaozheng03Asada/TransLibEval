import shutil
import os
from tempfile import NamedTemporaryFile, mkdtemp


class FileHandler:
    def handle_file_operations(self, content="Hello, world!", operation="copy", target_path=None):
       
        if operation == "copy":
            
            temp_file = NamedTemporaryFile(delete=False)
            with open(temp_file.name, 'w') as f:
                f.write(content)

            if target_path is None:
                temp_dir = mkdtemp()
                target_path = os.path.join(temp_dir, os.path.basename(temp_file.name))

            shutil.copy(temp_file.name, target_path)
            return temp_file.name, target_path 

        elif operation == "read":
            with open(content, 'r') as f:
                return f.read()

        elif operation == "exists":
            return str(os.path.exists(content))

        elif operation == "remove":
            if os.path.exists(content):
                os.remove(content)
            return "removed"

        else:
            raise ValueError("Unsupported operation. Choose from 'copy', 'read', 'exists', 'remove'.")
