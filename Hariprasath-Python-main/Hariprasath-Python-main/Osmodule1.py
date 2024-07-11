import os
import logging

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')

def manage_folder_and_file(folder_path, file_name):
   
    if not os.path.exists(folder_path):
        os.makedirs(folder_path)
        logging.info(f"Folder '{folder_path}' created.")
    else:
        file_path = os.path.join(folder_path, file_name)
        if os.path.exists(file_path):
            os.remove(file_path)
            logging.info(f"Existing file '{file_name}' deleted in folder '{folder_path}'.")

    new_file_path = os.path.join(folder_path, file_name)
    with open(new_file_path, 'w') as file:
        file.write("This is a new file.")
    logging.info(f"New file '{file_name}' created in folder '{folder_path}'.")

folder_path = "myfolder3"
file_name = "content2.txt"

manage_folder_and_file(folder_path, file_name)
