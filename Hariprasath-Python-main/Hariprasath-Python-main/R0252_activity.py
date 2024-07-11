import os
import logging
import datetime
import shutil
import R0252_activity
import sys
# Importing custom modules
# import moconfig
import helperFunctions_v4 as helperfunctions

# Setting up logging
print(os.getcwd())
source_file='demo.txt'
destination_file='content1.txt'

# log_file_path = os.path.join(os.getenv('AST_SCRIPTS'), 'R0252', 'R0252_activity.lis')

logging.basicConfig(filename="activity.log", level=logging.DEBUG, filemode="w", format="%(message)s" )

# Environment setting
os.chdir(os.path.dirname(__file__))
CURDIR = os.getcwd()
WGDIR = os.path.dirname(CURDIR)
REPTDIR = os.path.dirname(WGDIR)
AST_SCRIPTS = WGDIR

os.environ['CURDIR'] = CURDIR
os.environ['WGDIR'] = WGDIR
os.environ['REPTDIR'] = REPTDIR
os.environ['AST_SCRIPTS'] = AST_SCRIPTS



# R0252_activity.invoke()

# Set local variables
PROCESS = os.path.basename(__file__).split('.')[0]
LOG = os.path.join(AST_SCRIPTS, 'R0252', 'R0252_activity.lis')
MSG = os.path.join(AST_SCRIPTS, 'R0252', 'R0252_activity.msg')

# Permanent local vars
trl_recs = 0  # number of records reported in the trailer record
act_recs = 0  # number of actual detail records
hdr_found = 0  # boolean - denotes the header record was present

# Create log files
logging.info(f"Start {PROCESS}")

# logging.info("Server :  " + os.getenv('DSQUERY'))

# Date calculations
month = datetime.datetime.now().month
year = datetime.datetime.now().year
lastmonth = month - 1

if lastmonth == 0:
    lastmonth = 12
    year -= 1

if lastmonth <= 9:
    lastmonth = f'0{lastmonth}'

file = f'cibc{year}{lastmonth}'
file1 = file  # Seems to be a redundant variable in the original script

logging.info("2. GET AUDIT_LOG FILE.")
if not helperfunctions.copyFile(str(source_file), str(destination_file)):
    logging.error(f"**	-	ERROR : cp file <{source_file}> to local directory failed ...exiting")
    sys.exit(102)

# if not helperfunctions.file_exists_case_sensitive_generic(source_file,CURDIR):
#     logging.info(f"**	-	ERROR : input file <{file}> - not found .... exiting")
#     sys.exit(105)



# Check that the input file exists
if not os.path.isfile(source_file):
    logging.error(f"** - ERROR : input file <{source_file}> - not found .... exiting")
    exit(105)
logging.info(" - input file found - processing")

# Ensure the header record exists
with open(source_file, 'r') as f:
    hdr_found = 'HEADER' in f.readline()

if not hdr_found:
    logging.error(f"** - ERROR : Header record missing from file <{source_file}> ...exiting")
    exit(105)
logging.info(f" - Header record found in file <{source_file}> ...")

# Ensure the trailer record exists
with open(file, 'r') as f:
    for line in f:
        pass
    last_line = line
trl_found = 'TRAILER' in last_line

if not trl_found:
    logging.error(f"** - ERROR : Trailer record missing from file <{file}> ...exiting")
    exit(105)
logging.info(f" - Trailer record found in file <{file}> ...")

# Validate the header
if not os.path.isfile('./.last_header_rec'):
    logging.error("** - ERROR : could not load header record from last run ...exiting")
    exit(105)

with open('./.last_header_rec', 'r') as f:
    last_header_date = f.readline().split('-')[1]

with open(file, 'r') as f:
    new_header_date = f.readline().split('-')[1]

if new_header_date == last_header_date:
    logging.error("** - ERROR : This header date same as last file header date ... exiting")
    exit(105)
logging.info(" - Header create date different from previous file ... ")

# Save and remove the header before processing
with open(file, 'r') as f:
    lines = f.readlines()

with open('./.curr_header_rec', 'w') as f:
    f.write(lines[0])

# Validate the number of records reported in the trailer
trl_recs = int(last_line.split('-')[1])

# Strip off header and trailer and count actual detail rows
with open('./tmp_file', 'w') as f:
    f.writelines(lines[1:-1])

act_recs = len(lines) - 2

os.rename('./tmp_file', file)

# Compare trailer records and actual records
if trl_recs != act_recs:
    logging.error(f"** - ERROR : trailer detail rows = <{trl_recs}>  vs  actual detail rows = <{act_recs}> in file <{file}> ...exiting")
    exit(105)

logging.info(f" - trailer detail rows = <{trl_recs}>  actual detail rows = <{act_recs}>  for file <{file}> ...")

  # Importing the helperfunctions module

# Oracle statement to drop table if exists, wrapped in an anonymous PL/SQL block
drop_table_sql = """
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE audit_log_load';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
"""

# Oracle statement to create table, wrapped in an anonymous PL/SQL block
create_table_sql = """
BEGIN
   EXECUTE IMMEDIATE '
   CREATE TABLE audit_log_load (
   ACCOUNT VARCHAR2(20),
   ACCOUNT_TYPE VARCHAR2(20),
   CURRENCY VARCHAR2(20),
   LOGIN VARCHAR2(20),
   GROUP1 VARCHAR2(20),
   CHANNEL VARCHAR2(20),
   LOB VARCHAR2(20),
   OWNERSHIP VARCHAR2(20),
   FUNCTION_CODE VARCHAR2(20),
   Total_Successful_hits VARCHAR2(20),
   Total_Failed_hits VARCHAR2(180)
   )';
END;
"""

# Execute the drop table SQL statement
if not helperfunctions.executeSQL(drop_table_sql):
    logging.error("Error in executing DROP TABLE sql")
    sys.exit(101)

# Execute the create table SQL statement
if not helperfunctions.executeSQL(create_table_sql):
    logging.error("Error in executing CREATE TABLE sql")
    sys.exit(101)

if not helperfunctions.importTableDataFromCSV(f'{file}, {dev_db}..audit_log_load, False, ";"'):
    logging.error("Error 103 BCP audit_log_load Failed")
    sys.exit(103)
else:
    logging.info("bcp audit_log_load worked")

  # Assuming this module contains the necessary database connection and execution logic

# Oracle PL/SQL block for setting module name
query_set_module = """
BEGIN
    DBMS_APPLICATION_INFO.SET_MODULE(module_name => :PROCESS, action_name => NULL);
END;
"""
if not helperfunctions.executeSQL(query_set_module):
    logging.error("Error in executing sql for setting module name")
    sys.exit(101)

# Oracle PL/SQL block for altering session schema
query_alter_session = """
BEGIN
    EXECUTE IMMEDIATE 'ALTER SESSION SET CURRENT_SCHEMA = :db_dev';
END;
"""
if not helperfunctions.executeSQL(query_alter_session):
    logging.error("Error in executing sql for altering session schema")
    sys.exit(101)

# Oracle PL/SQL block for creating index idx_FUNCTION_CODE
query_create_index_function_code = """
BEGIN
    EXECUTE IMMEDIATE 'CREATE INDEX idx_FUNCTION_CODE ON audit_log_load (FUNCTION_CODE)';
END;
"""
if not helperfunctions.executeSQL(query_create_index_function_code):
    logging.error("Error in executing sql for creating index idx_FUNCTION_CODE")
    sys.exit(101)

# Oracle PL/SQL block for creating index idx_ACCOUNT
query_create_index_account = """
BEGIN
    EXECUTE IMMEDIATE 'CREATE INDEX idx_ACCOUNT ON audit_log_load (ACCOUNT)';
END;
"""
if not helperfunctions.executeSQL(query_create_index_account):
    logging.error("Error in executing sql for creating index idx_ACCOUNT")
    sys.exit(101)


os.remove(file)

if not helperfunctions.file_exists_case_sensitive_generic('./.last_header_rec'):
    logging.info("**	-	ERROR : could not load header record from last run ...exiting")
    sys.exit(105)

# Check log file for error messages
logging.info("Checking LOG file for errors")

# Not Applicable

if not helperfunctions.moveFile(source_file, destination_file):
    logging.error("Moving of ./.curr_header_rec failed!")
    sys.exit(105)

logging.info(f"The Process {PROCESS} completed")
