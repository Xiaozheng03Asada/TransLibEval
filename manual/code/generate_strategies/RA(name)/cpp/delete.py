import os
import json


def remove_backslashes_from_query_result(json_data):
    """
    删除所有"query_result"中的反斜杠（\"）。
    """
    for item in json_data:
        if 'query_result' in item:
            item['query_result'] = item['query_result'].replace('\\"', '"')
    return json_data


def process_json_files_in_folder(folder_path):
    """
    读取指定文件夹中的所有 JSON 文件，删除每个文件中 query_result 中的反斜杠，保存修改后的文件。
    """
    for filename in os.listdir(folder_path):
        if filename.endswith(".json"):
            file_path = os.path.join(folder_path, filename)
            try:
                with open(file_path, 'r', encoding='utf-8') as f:
                    data = json.load(f)

                # 处理数据
                updated_data = remove_backslashes_from_query_result(data)

                # 保存修改后的文件
                with open(file_path, 'w', encoding='utf-8') as f:
                    json.dump(updated_data, f, ensure_ascii=False, indent=4)
                print(f"处理完成: {file_path}")
            except Exception as e:
                print(f"处理文件 {file_path} 时出错: {e}")


# 使用示例，替换为你实际的文件夹路径
folder_path = '/Users/asada/Documents/MyWorkplace/检索-cpp/DeepSeek-V3/py_api_results'
process_json_files_in_folder(folder_path)
