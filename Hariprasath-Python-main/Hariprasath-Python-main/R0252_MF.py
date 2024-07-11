
import os
import datetime
import logging
import shutil
import subprocess
import helperFunctions_v3 as helperfunctions

import sys
# Setting up logging
# print(os.getenv('logfiles.t'))
# log_file_path = os.path.join(os.getenv('logfiles.txt'))
logging.basicConfig(filename='logfiles.txt', level=logging.INFO, format='%(asctime)s:%(levelname)s:%(message)s')

# Environment setting
CURDIR = os.path.dirname(os.path.abspath(__file__))
WGDIR = os.path.dirname(CURDIR)
REPTDIR = os.path.dirname(WGDIR)
AST_SCRIPTS = WGDIR

MF_file = 'R0252_RS_MF.TXT'
file_name = 'protagonist.csv'
RS_file = 'MF_BALANCES.TXT'
file1_name='mydata.csv'
# Set local variables
PROCESS = os.path.basename(__file__).split('.')[0]
LOG ='demo' 
MSG ='demo1.txt'
err_ksh='105'
sftp_out_lz_nas='Hari_297'
shared_files='content1.txt'
db_dev='ksh'
sftp_in=os.getcwd()
# Create log files
with open('demo', 'w') as log_file, open('demo1.txt', 'w') as msg_file:
    current_time = datetime.datetime.now()
    log_file.write(f"Start {PROCESS} on {current_time}\n")
    msg_file.write(f"Start {PROCESS} on {current_time}\n")
    log_file.write("\n")
    log_file.write(f"Server :  {os.getenv('DSQUERY')}\n")

logging.info("ON INITIAL RUN EXECUTE THE DDL SCRIPT: 1047_DDL.ksh on %s", datetime.datetime.now())

if os.path.isfile("1047_DDL.lis"):
    logging.info("DDL log exists, checking for errors at %s", datetime.datetime.now())
#   check_error.invoke('1047_DDL.lis')  
helperfunctions.invoke('1047_DDL.lis')

if False:  # Placeholder for error check
    logging.info("Errors found, re-running DDL SQL at %s", datetime.datetime.now())
 
if False:  # Placeholder for error check after re-running DDL SQL
    logging.error("Error creating stored procedure at %s", datetime.datetime.now())
    exit(102)
else:
        logging.info("Created stored procedure at %s", datetime.datetime.now())


if False:  # Placeholder for error check
    logging.error("Error Code 101 - SQL errors found in DDL log at %s", datetime.datetime.now())
    exit(101)
else:
    logging.info("Stored procedure completed successfully at %s", datetime.datetime.now())
# else:
#     logging.info("Stored procedure already run, skipping at %s", datetime.datetime.now())

 

if False:  # Placeholder for error check after attempting to create stored procedure
        logging.error("Error creating stored procedure at %s", datetime.datetime.now())
        exit(102)
else:
    logging.info("Created stored procedure at %s", datetime.datetime.now())

# check_error.invoke('1047_DDL.lis')    
helperfunctions.invoke('1047_DDL.lis')

if False:  # Placeholder for error check
        logging.error("Error Code 101 - SQL errors found in DDL log at %s", datetime.datetime.now())
        exit(101)
else:
        logging.info("Stored procedure completed successfully at %s", datetime.datetime.now())


logging.info("1.1 Get CIBC_FUND.csv tables(for future use - change driver 1301-1404 by table driven) on %s", datetime.datetime.now())

logging.info("Copying file %s from the $sftp_in dir on %s", file_name, datetime.datetime.now())
if not helperfunctions.copyFile(f'{sftp_in}/{file_name}', f'./{file1_name}'):
    logging.error(f"Error: {err_ksh}a, file copy {file_name} failed in dir {CURDIR}  on {datetime.datetime.now()}")
    sys.exit(err_ksh)

logging.info("1.2 Create CIBCDEV..CIBC_FUND %s", datetime.datetime.now())



# Dropping the CIBC_FUND table if it exists
drop_table_query = """
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE CIBC_FUND';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
"""
# if not helperfunctions.executeSQL(drop_table_query):
#     logging.error("Error in executing sql for dropping table")
#     sys.exit(101)

# Creating the CIBC_FUND table
create_table_query = """
BEGIN
   EXECUTE IMMEDIATE 'CREATE TABLE Hari_297_FUND
   ( symbol        VARCHAR2(6), 
     ti_desc_1     VARCHAR2(50), 
     MF_Type       VARCHAR2(25),
     ti            NUMBER,
     driver        CHAR(4))';
END;
"""
# if not helperfunctions.executeSQL(create_table_query):
#     logging.error("Error in executing sql for creating table")
#     sys.exit(101)


if False:  # Placeholder for error check after CREATE TABLE
    logging.error("Error 101 failed to CREATE TABLE CIBC_FUND on %s", datetime.datetime.now())
    exit(101)
else:
    logging.info("The CREATE TABLE CIBC_FUND run successfully on %s", datetime.datetime.now())

logging.info("BCP CIBC_FUND table in on %s", datetime.datetime.now())
strSQL = """
    Select * From Hari_297_FUND
"""
# if not helperfunctions.importTableDataFromCSV(file_name,strSQL , False, ","):
#     logging.error("Error 103a BCP CIBC_FUND Failed")
#     sys.exit(103)

if False:  # Placeholder for BCP error check
    logging.error("Error 103a BCP CIBC_FUND Failed")
    exit(103)
else:
    logging.info("bcp CIBC_FUND worked")

logging.info("2. RUN SP af_R0252_MF/af_R0252_EQ/af_R0252_MF_trailer %s", datetime.datetime.now())
  # Importing the helperfunctions module

# Oracle PL/SQL block for executing af_R0252_MF(0)
# plsql_block_af_R0252_MF = """
# BEGIN
#     EXECUTE IMMEDIATE 'BEGIN AF_Hari_297_R0252_MF(0); END;';
# END;
# """
# # Execute the PL/SQL block using helperfunctions.executeSQL
# if not helperfunctions.executeSQL(plsql_block_af_R0252_MF):
#     logging.error("Error in executing af_R0252_MF(0)")
#     sys.exit(101)

# # Oracle PL/SQL block for executing af_R0252_EQ_NEW
# plsql_block_af_R0252_EQ_NEW = """
# BEGIN
#     EXECUTE IMMEDIATE 'BEGIN af_Hari_297_R0252_EQ_NEW; END;';
# END;
# """
# # Execute the PL/SQL block using helperfunctions.executeSQL
# if not helperfunctions.executeSQL(plsql_block_af_R0252_EQ_NEW):
#     logging.error("Error in executing af_R0252_EQ_NEW")
#     sys.exit(101)

# # Oracle PL/SQL block for executing af_R0252_MF_trailer
# plsql_block_af_R0252_MF_trailer = """
# BEGIN
#     EXECUTE IMMEDIATE 'BEGIN af_Hari_297_R0252_MF_trailer; END;';
# END;
# """
# # Execute the PL/SQL block using helperfunctions.executeSQL
# if not helperfunctions.executeSQL(plsql_block_af_R0252_MF_trailer):
#     logging.error("Error in executing af_R0252_MF_trailer")
#     sys.exit(101)


if False:  # Placeholder for SQL execution error check
    logging.error("Error 101b failed to execute SQL on %s", datetime.datetime.now())
    exit(101)
else:
    logging.info("The SQL executed successfully on %s", datetime.datetime.now())

logging.info("Checking LOG file for errors on %s", datetime.datetime.now())
# check_error.invoke(LOG)
helperfunctions.invoke(LOG)
if False:  # Placeholder for log file error check
    logging.error("Error 105 on %s", datetime.datetime.now())
    exit(105)
create_table_query=""
logging.info("BCP R0252_RS_MF table in on %s", datetime.datetime.now())
if not helperfunctions.exportTableDataToCSV(MF_file,strSQL):
    logging.error("Error 103a BCP R0252_RS_MF Failed")
    sys.exit(103)

if False:  # Placeholder for BCP error check
    logging.error("Error 103a BCP R0252_RS_MF Failed")
    exit(103)
else:
    logging.info("bcp R0252_RS_MF worked")

logging.info("3. Get rid of the position 81 in the file. %s", datetime.datetime.now())
with open(MF_file, 'r') as original, open(RS_file, 'w') as modified:
    for line in original:
        modified.write(line[:80] + '\n')

logging.info("4. Copy THE FILES INTO THE ${sftp_out_lz_nas} folder. %s", datetime.datetime.now())
if not helperfunctions.copyFile(f'{AST_SCRIPTS}/R0252/{RS_file}', f'{sftp_out_lz_nas}/{RS_file}'):
    logging.error(f"Error {err_ksh}b, Failed to copy file {RS_file} to {sftp_out_lz_nas} folder on {datetime.now()}")
    sys.exit(err_ksh)

logging.info("5. Copy THE FILES INTO THE ${shared_files} folder. %s", datetime.datetime.now())
if not helperfunctions.copyFile(f'{AST_SCRIPTS}/R0252/{RS_file}', f'{shared_files}/{RS_file}'):
    logging.error(f"Error {err_ksh}b, Failed to copy file {RS_file} to {shared_files} folder on {datetime.now()}")
    sys.exit(err_ksh)

logging.info("7. Delete file MF_BALANCES.TXT.Z . %s", datetime.datetime.now())
os.remove(MF_file)
os.remove(file_name)
os.remove(RS_file)
print("completed")

logging.info("**********************************************************************")
logging.info("The Process %s completed at %s", PROCESS, datetime.datetime.now())
