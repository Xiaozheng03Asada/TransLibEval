import shutil
import os


class FileMover:
    
    def perform_task(self, task, args=""):
        
        if task == 'move':
            src, dest = args.split(",")
            if not os.path.exists(src):
                raise FileNotFoundError(f"Source path '{src}' does not exist")
            
            if not os.path.exists(dest):
                os.makedirs(dest)
            return shutil.move(src, dest)

        elif task == 'create_file':
            file_path, content = args.split(",", 1)
            with open(file_path, 'w') as f:
                f.write(content)

        elif task == 'create_directory':
            dir_path = args
            os.makedirs(dir_path, exist_ok=True)

        elif task == 'path_exists':
            path = args
            return str(os.path.exists(path))

        elif task == 'remove_path':
            path = args
            if os.path.isdir(path):
                shutil.rmtree(path)
            elif os.path.isfile(path):
                os.remove(path)

        else:
            raise ValueError(f"Unknown task '{task}' specified.")
