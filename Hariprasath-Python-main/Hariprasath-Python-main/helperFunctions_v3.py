from datetime import datetime
import logging
import sys
import os
import csv
import subprocess
import cx_Oracle
import traceback
import shutil
print(cx_Oracle.version)

"""
Created By:     Umesh Bhavsar 
Created Date:   02/12/2024
Purpose:        Central repository for common helper functions  
                which can be used across all scripts.           
"""

PYTHON_PASSWORD = "ksh"
PYTHON_USER = "ksh"
PYTHON_DSN = "172.24.0.200:1521/ksh"

ora_connection = cx_Oracle.connect(PYTHON_USER, PYTHON_PASSWORD, PYTHON_DSN)

cx_Oracle_cursor = cx_Oracle.CURSOR

ora_cursor = ora_connection.cursor()


sftp_incoming_path = os.getcwd()

#logging.basicConfig(filename="helperFunctions.log", level=logging.DEBUG, filemode="w", format="%(asctime)s %(message)s", datefmt="%m/%d/%Y %I:%M:%S %p")
logging.basicConfig(filename="helperFunctions2.log", level=logging.DEBUG, filemode="w", format="%(message)s" )

logging.info("====================================================================================================================")
logging.info("")

logging.info("Derive Environment START")
logging.info("")

currentWorkingDirectory = os.getcwd()
logging.info("Current Working Directory is %s", currentWorkingDirectory)
logging.info("")

currentWorkingDirectoryParts = currentWorkingDirectory.split('/')
environment_name = 'DEV' 

logging.info("Environment is %s", environment_name)
logging.info("")

logging.info("Derive Environment END")
logging.info("")

logging.info("Importing moconfig file from batch directory - START")
logging.info("")

try:
    sys.path.insert(1, '/shared/cibc/' + environment_name + '/batch')

    logging.info("Batch directory path inserted")
    logging.info("")

    # import moconfig

    logging.info("Importing moconfig file from batch directory - END")

    logging.info("")
except:
    logging.info("An error occurred while importing moconfig.py")
    logging.info("")
    exit()

def dropTempTables(tblName):
    logging.info("Checking if Table %s is already created", tblName)
    logging.info("")

    tblExists = ''

    rows = ora_cursor.execute("select table_name from user_tables where table_name = '" + tblName.upper() + "'")

    for row in rows:
        tblExists = row[0]

    if tblExists:
        logging.info("Table %s is already created. Dropping it off", tblName)
        logging.info("")
        ora_cursor.execute("drop table " + tblName)
        ora_connection.commit()
        logging.info("Table %s dropped", tblName)
        logging.info("")
    else:
        logging.info("Table %s don't exists.", tblName)
        logging.info("")
        

def dropCreateTempTables(tblName, tblSchema):
    logging.info("Checking if Table %s is already created", tblName)
    logging.info("")

    ora_cursor.execute("select table_name from user_tables where table_name = '" + tblName.upper() + "'")
   
    if ora_cursor.fetchone() != None:
        logging.info("Table %s is already created. Dropping it off", tblName)
        logging.info("")
        ora_cursor.execute("drop table " + tblName)
        ora_connection.commit()
        logging.info("Table %s dropped", tblName)
        logging.info("")

    logging.info("Creating table %s", tblName)
    logging.info("")

    ora_cursor.execute(tblSchema)
    ora_connection.commit()

    logging.info("Table %s created", tblName)
    logging.info("")

def createIndexOnTable(tblName, columnName, indexName):
    logging.info("Creating index on %s column in %s table.", columnName, tblName)
    logging.info("")
    print(len(indexName))
    print(len(columnName))
    ora_cursor.execute("create index " + indexName + " on " + tblName + "(" + columnName + ")")
    ora_connection.commit()

    logging.info("Index %s created successfully on %s column in %s table.", indexName, columnName, tblName)
    logging.info("")

def checkAndCreateDirectory(dirToCheckAndCreate, path):
    logging.info("--------------------------------------------------------------------------------------")
    logging.info("")

    logging.info("Check if %s Directory exists.", dirToCheckAndCreate)
    logging.info("")

    report_directory = os.path.join(path, dirToCheckAndCreate)

    file_exists = os.path.exists(report_directory)

    if file_exists:
        logging.info("Directory %s exists.", dirToCheckAndCreate)
        logging.info("")
    else:
        os.mkdir(report_directory, 0o755)
        logging.info("%s Directory created", dirToCheckAndCreate)
        logging.info("")

    logging.info("--------------------------------------------------------------------------------------")
    logging.info("")

def exportTableDataToCSV(filePath, strSQLToExecute):
    ora_cursor.arraysize = 100
    outputFileHandle = open(filePath, "w")
    writer = csv.writer(outputFileHandle, lineterminator="\n")
    writer.writerows(ora_cursor.execute(strSQLToExecute))

def exportTableDataToCSVSP(filePath, strSQLToExecute):
    result_cursor = ora_cursor.var(cx_Oracle_cursor)
    ora_cursor.callproc(strSQLToExecute, [result_cursor])
    result = result_cursor.getvalue()
    ora_cursor.arraysize = 100
    outputFileHandle = open(filePath, "w")
    writer = csv.writer(outputFileHandle, lineterminator="\n")
    writer.writerows(result)

def exportTableDataToCSVWithHeader(filePath, strSQL):
    ora_cursor.execute(strSQL)
    rows = ora_cursor.fetchall()
    columns = [column[0].lower() for column in ora_cursor.description]
    outputFile = open(filePath, "w")
    writer = csv.writer(outputFile, lineterminator="\n")
    writer.writerow(columns)
    writer.writerows(rows)

def importTableDataFromCSV(filePath, strSQL, skipHeader = True, specialCharacter = ''):
   with  open(filePath, 'r') as csvfile:
        csv_reader = csv.reader(csvfile)
        
        if skipHeader == True:
            #skip the header row
            next(csv_reader, None)

        #Convert remaining CSV rows to list of tuples
        if specialCharacter == '':
            data_to_insert = [tuple(row) for row in csv_reader]
        else:
            data_to_insert = [tuple(row[0].split(specialCharacter)) for row in csv_reader] 
        ora_cursor.executemany(strSQL,data_to_insert)
        ora_connection.commit()

def readDataFromFile(filePath):
   with  open(filePath, 'r') as inputfile:
        fileData = inputfile.read()
        
        return fileData	

def checkAndRemoveDirectory(dirToCheckAndRemove, path):
    logging.info("--------------------------------------------------------------------------------------")
    logging.info("")

    logging.info("Check if %s Directory exists.", dirToCheckAndRemove)
    logging.info("")

    report_directory = os.path.join(path, dirToCheckAndRemove)

    dir_exists = os.path.exists(report_directory)

    if dir_exists:
        logging.info("Directory %s exists.", dirToCheckAndRemove)
        logging.info("")
        logging.info("Removing Directory %s.", dirToCheckAndRemove)
        logging.info("")
        os.system("rm -r " + report_directory)
    else:
        logging.info("%s Directory does not exist.", dirToCheckAndRemove)
        logging.info("")

    logging.info("--------------------------------------------------------------------------------------")
    logging.info("")


def convert_to_lowercase(filename):
    return filename.lower()

def file_exists_case_sensitive(filename):
    # check if file exists using case-sensitive filename matching
    if os.path.exists(sftp_incoming_path + "/" + filename):
        # on case-insensitive file systems, check if the actual filename matches
        if os.path.basename(sftp_incoming_path + "/" + filename) in os.listdir(sftp_incoming_path):
            return True
    return False

def file_exists_case_sensitive_generic(filepath, filename):
    # check if file exists using case-sensitive filename matching
    if os.path.exists(filepath + "/" + filename):
        # on case-insensitive file systems, check if the actual filename matches
        if os.path.basename(filepath + "/" + filename) in os.listdir(filepath):
            return True
    return False
	
def overwrite_header(csv_file, old_header,new_header):
    # Open and read the contents of csv file 
    with  open(csv_file, 'rb') as readcsvfile:
        reader = csv.reader(readcsvfile)
        rows = list(reader)

        # Find the index of old header
        header_index = None
        if rows:
            if old_header in rows[0]:
                header_index = rows[0].index(old_header)

        if header_index is not None:
            # Overwrite the old header with new header
            rows[0][header_index] = new_header


            # Write back the modified contents to CSV file
            with open(csv_file,'wb') as overwritefile:
                csvwriter = csv.writer(overwritefile)
                csvwriter.writerows(rows)
        
            logging.info("Header overwritten successful.")
        else:
            logging.info("Old header not found.")

def runLinuxCommand(commandToRun):
    result = subprocess.check_output(commandToRun, shell=True)
    return result

def exportTableDataToCSVWithSeparator(filePath, strSQLToExecute, separator):
    ora_cursor.arraysize = 100
    outputFileHandle = open(filePath, "w")
    writer = csv.writer(outputFileHandle, lineterminator="\n", delimiter=separator)
    writer.writerows(ora_cursor.execute(strSQLToExecute))

def dos2unix(filename):
    # with open(filename, 'rb') as f:
    #     content = f.read().replace(b'\r\n', b'\n')
   
    # with open(filename, 'wb') as f:
    #     f.write(content)
    file = replaceContent(file_path= filename)



def replaceContent(file_path, outputfile=None, chunk_size=100, old="\r\n", new='\n', delimiter=None, position=None):
    replace = False
    if outputfile is None:
        replace = True
        outputfile = f"output_{datetime.now().strftime('%Y-%m-%d_%H-%M-%S')}.txt"
    if not os.path.exists(outputfile):
        open(outputfile, 'w').close()
    with open(file_path, 'r') as file, open(outputfile, 'w') as tempfile:
            lines_read = 0
            chunk = []
            for line in file:
                lines_read += 1
                chunk.append(line)
                if len(chunk) <= chunk_size:
                    for line in chunk:
                        if old or new:
                            line = line.replace(old, new)
                        elif delimiter:
                            line = line.split(delimiter)
                            if position:
                                line = line[position]
                        tempfile.write(line)
                    chunk = []  
    if replace:
        os.replace(outputfile, file_path)

def get_head_tail_lines(inputFile, head=None, tail=None, delimiter=None, position=None):
    head_lines = []
    tail_lines = []

    with open(inputFile, 'r') as f_in:
        if head is not None:
            for _ in range(head):
                line = f_in.readline().strip()
                if not line:
                    break
                if delimiter:
                    line = line.split(delimiter)
                    if position:
                        line = line[position]
                head_lines.append(line)
        if tail is not None:
            with open(inputFile, 'rb') as file:
                file.seek(-4 * tail, 2)
                while len(tail_lines) < tail:
                    while file.read(1) != b'\n':
                        file.seek(file.tell() - 2, 0)  # Move back one more byte
                    last_line = file.readline().decode().strip()
                    if delimiter:
                        line = last_line.split(delimiter)
                        if position:
                            line = line[position]
                    tail_lines.append(last_line)
    return head_lines, list(reversed(tail_lines))


def read_file_in_chunks(file_path, outputfile=None, chunk_size=100, skip_first=0, skip_last=1, delimiter=None, position=None, join_str=None):
    replace = False
    if outputfile is None:
        replace = True
        outputfile = f"output_{datetime.now().strftime('%Y-%m-%d_%H-%M-%S')}.txt"
    if not os.path.exists(outputfile):
        open(outputfile, 'w').close()

    with open(file_path, 'r') as file:
        lines = file.readlines()

    # Skip first lines
    lines = lines[skip_first:]

    # Skip last lines
    if skip_last > 0:
        lines = lines[:-skip_last]

    with open(outputfile, 'w') as file2:
        chunk = []
        for line in lines:
            chunk.append(line.strip())  # Strip newline characters

            if len(chunk) >= chunk_size:
                for c_line in chunk:
                    if delimiter:
                        c_line_parts = c_line.split(delimiter)
                        if position is not None:
                            c_line_parts = [c_line_parts[position]]
                        if join_str is not None:
                            c_line = join_str.join(c_line_parts)
                    file2.write(c_line + "\n")
                chunk = []

        # Write any remaining lines in the chunk
        for c_line in chunk:
            if delimiter:
                c_line_parts = c_line.split(delimiter)
                if position is not None:
                    c_line_parts = [c_line_parts[position]]
                if join_str is not None:
                    c_line = join_str.join(c_line_parts)
            file2.write(c_line + "\n")

    if replace:
        os.replace(outputfile, file_path)

def count_lines(filename):
    line_count = 0
    with open(filename, 'r') as f:
        for line in f:
            line_count += 1
    print(line_count)
    return line_count

    
def create_file_update(strQFPath, strQRStandardFileName):
    file_path = os.path.join(strQFPath, strQRStandardFileName)
    if not os.path.exists(file_path):
        with open(file_path, 'w'):
            pass 
        

def change_file_permissions(strQFPath, strQRStandardFileName):
    file_path = os.path.join(strQFPath, strQRStandardFileName)
    permission_mode = 0o777  
    try:
        os.chmod(file_path, permission_mode)
        print(f"Changed permissions of '{file_path}' to {permission_mode:o}.")
    except OSError as e:
        print(f"Error occurred while changing permissions of '{file_path}': {e}")

def executeSQL(sqlToExecute):
    try:
        print("EXECUTE STATEMENT",sqlToExecute)
        ora_cursor.execute(sqlToExecute)
        return True
    except Exception as e:
        traceback.print_exc() 
        logging.error(f"Error in executing query{sqlToExecute}. Reason:{e}")
    return False    
def copyFile(source, destination):
    print(f"File copied successfully from {source} to {destination}")
    try:
        shutil.copy(source, destination)
        return True  # Return True if copy was successful
    except Exception as e:
        print(traceback.print_exc())
        logging.error(f"Error in copying the file from {source} to {destination}. Reason:{e}")
        
        
        
def executeQuery(sqlToExecute):
    try:
        print("EXECUTE STATEMENT",sqlToExecute)
        ora_cursor.execute(sqlToExecute)
        return ora_cursor.fetchall()
    except Exception as e:
        traceback.print_exc() 
        logging.error(f"Error in executing query{sqlToExecute}. Reason:{e}")      


def get_table_name(table_name):
    try:
        query = f"SELECT * FROM {table_name}"
        ora_cursor.execute(query)
        with open(f'Hari_297_ER0108_{table_name}.txt', 'w') as output_file:
            rows = ora_cursor.fetchall()
        for row in rows:
            line = '|'.join(map(str, row))
            output_file.write(line + '\n')
            print(f"Data from table '{table_name}' has been written to Hari_297_ER0108_{table_name}.txt")

    except cx_Oracle.Error as error:
        print(f"Error occurred: {error}")
        
def invoke(filename):
        # Your code to handle the file goes here
        # For example, it could read the file and process it for errors
        try:
            with open(filename, 'r') as file:
                contents = file.read()
                # Process the file contents
                print(f"Processing file: {filename}")
                # Perform error checking
                # self.check_errors(contents)
        except FileNotFoundError:
            print(f"File {filename} not found.")
        except Exception as e:
            print(f"An error occurred: {e}")

def check_errors(self, contents):
        # Your code to check for errors in the file contents
        if "ERROR" in contents:
            print("Error found in file!")
        else:
            print("No errors found.")        
        