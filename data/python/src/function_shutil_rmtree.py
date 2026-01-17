import shutil
import os

class DirectoryDeleter:
    
    def delete_directory_tree(self, directory_path: str) -> str:
        
        if not os.path.exists(directory_path):
            return f"Directory '{directory_path}' does not exist."
        
        try:
            shutil.rmtree(directory_path)
            return f"Directory '{directory_path}' deleted successfully."
        except Exception as e:
            return f"Failed to delete directory '{directory_path}': {str(e)}"
