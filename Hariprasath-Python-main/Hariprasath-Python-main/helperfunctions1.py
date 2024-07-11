import os
import logging
import shutil
import csv
  


incoming_path=os.getcwd() 
logging.basicConfig(filename="helperFunctions1.log", level=logging.DEBUG, filemode="w", format="%(message)s" )
def file_exists_case_sensitive_generic(filename,filepath):
    try:
        # print((filepath +'/'+filename))
        if os.path.exists(filepath +'/'+filename):
            if os.path.basename(filepath +'/'+filename) in os.listdir(filename):
                return True
        return False
    except Exception as e:
        logging.exception(f"Exception occured file_exists_case_sensitive_generic {incoming_path,incoming_path } ::: {e}")


def copyFile(source,destination):
    try:
        logging.info("copying file from {source} to {destination}")
        shutil.copy(source,destination)
        return True
    except Exception as e:

        logging.error(f"Error in copying the file from {source} to {destination}. Reason:{e}")

def moveFile(source,destination):
    try:
        logging.info("moving file from {source} to {destination}")
        shutil.move(source,destination)
        return True
    except Exception as e:
        logging.error(f"Error in moving the file from {source} to {destination}. Reason:{e}")

def importTableDataFromCSV(filePath, strSQL, skipHeader = True, specialCharacter = ''):
    try:
        with open(filePath,'r') as csvfile:
            csv_reader=csv.reader(csvfile)
            if skipHeader==True:
                next(csv_reader,None)

            if specialCharacter == '':
                data_to_insert = [tuple(row) for row in csv_reader]
            else:
                data_to_insert = [tuple(row[0].split(specialCharacter)) for row in csv_reader]    

    except Exception as e:
        logging.exception(f"Exception occured importTableDataFromCSV {filePath, strSQL} ::: {e}")
