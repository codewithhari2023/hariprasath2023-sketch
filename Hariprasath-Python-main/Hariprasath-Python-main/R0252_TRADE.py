import os
import datetime
import logging
import sys
from pathlib import Path
import helperFunctions_v3 as helperfunctions

# Setting up logging
log_file_path = os.path.join(os.getenv('logfiles.txt'), 'R0252', 'R0252_TRADE.lis')
logging.basicConfig(filename=log_file_path, level=logging.INFO, format='%(asctime)s:%(levelname)s:%(message)s')

# Environment setting
CURDIR = os.path.dirname(os.path.abspath(__file__))
WGDIR = os.path.dirname(CURDIR)
REPTDIR = os.path.dirname(WGDIR)
AST_SCRIPTS = WGDIR
file_name_5='mydata.txt'
# Set local variables
PROCESS = os.path.basename(__file__).split('.')[0]
LOG = os.path.join(AST_SCRIPTS, 'R0252', 'R0252_TRADE.lis')
MSG = os.path.join(AST_SCRIPTS, 'R0252', 'R0252_TRADE.msg')
sftp_in=os.getcwd()
COMMISSION_file='content1.txt'
TRADES_file='content3.txt'
err_ksh='105' 
# Create log files
with open(LOG, 'w') as log_file, open(MSG, 'w') as msg_file:
    current_time = datetime.datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    log_file.write(f"Start {PROCESS} on {current_time}\n")
    msg_file.write(f"Start {PROCESS} on {current_time}\n")

logging.info("Server :  $DSQUERY")



# Check for DDL log file
if os.path.isfile("1048_DDL.lis"):
    logging.info("DDL log exists, checking for errors at `date` \n")
  
helperfunctions.invoke('1048_DDL.lis')

 
# CURDIR_1048_DDL.invoke()

    
# check_error.invoke('1048_DDL.lis')
# else:
# CURDIR_1048_DDL.invoke()


helperfunctions.invoke('1048_DDL.lis')

# Set local variables for file names
file_names = ["CIBC_TRANSFER.csv", "BLOTTER_CODE.csv", "TRACK1_TABLE.csv", "DRIVER_3040.csv", "Y03_last_mth.csv"]
month = datetime.datetime.now().month
year = datetime.datetime.now().year
lastmonth = month - 1

if lastmonth == 0:
    lastmonth = 12
    year -= 1

lastmonth = f"{lastmonth:02d}"

prevmth2 = lastmonth - 1
if prevmth2 == 0:
    prevmth2 = 12

prevmth2 = f"{prevmth2:02d}"

# Copying files
for reportname in file_names:
    logging.info(f"Copying file {reportname} from ${sftp_in} folder on  {datetime.datetime.now()}")
    if not helperfunctions.copyFile(f'{sftp_in}/{reportname}', f'./{reportname}'):
        logging.error(f"Error: {err_ksh}, file copy {reportname} failed in dir {CURDIR}  on {datetime.now()}")
    sys.exit(err_ksh)

# Header/Trailer verification and processing
for filename in [f"login_channel.txt{year}{lastmonth}", f"login_group.txt{year}{lastmonth}"]:
    logging.info(f"Processing input file ${sftp_in}/{filename}")
    file_start = filename.split(".")[0]
    curr_header = f"{file_start}_curr_header"
    prev_header = f"{file_start}_prev_header"

    # Populate current header file and verify header is set.
    with open(os.path.join(sftp_in, filename), 'r') as file:
        first_line = file.readline()
        if 'HEADER' not in first_line:
            logging.error(f"** ERROR: Header record missing from {filename} ...exiting \n")
            sys.exit(err_ksh)
        else:
            logging.info(f"\tHeader record found in {filename} ...")

 # R0252_copy_Informatica.invoke(year, lastmonth)
helperfunctions.invoke(year, lastmonth)

# SQL operations

 # Assuming helperfunctions.py contains the necessary database connection and execution logic

# Dropping R0252_login_channel table
drop_login_channel = """
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE R0252_login_channel';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
"""
if not helperfunctions.executeSQL(drop_login_channel):
    logging.error("Error in executing sql for dropping R0252_login_channel table")
    sys.exit(101)

# Creating R0252_login_channel table
create_login_channel = """
CREATE TABLE R0252_login_channel (
   userid varchar2(40),
   logins varchar2(40),
   channel varchar2(40),
   user_group varchar2(100)
)
"""
if not helperfunctions.executeSQL(create_login_channel):
    logging.error("Error in executing sql for creating R0252_login_channel table")
    sys.exit(101)

# Dropping R0252_login_group table
drop_login_group = """
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE R0252_login_group';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;0
"""
if not helperfunctions.executeSQL(drop_login_group):
    logging.error("Error in executing sql for dropping R0252_login_group table")
    sys.exit(101)

# Creating R0252_login_group table
create_login_group = """
CREATE TABLE R0252_login_group (
   userid varchar2(40),
   org_id varchar2(40),
   user_group varchar2(40),
   account_id varchar2(100)
)
"""
if not helperfunctions.executeSQL(create_login_group):
    logging.error("Error in executing sql for creating R0252_login_group table")
    sys.exit(101)

 # Dropping R0252_Y03_last_mth table
drop_Y03_last_mth = """
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE R0252_Y03_last_mth';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
"""
if not helperfunctions.executeSQL(drop_Y03_last_mth):
    logging.error("Error in executing sql for dropping R0252_Y03_last_mth table")
    sys.exit(101)

# Creating R0252_Y03_last_mth table
create_Y03_last_mth = """
CREATE TABLE R0252_Y03_last_mth (
   client char(20),
   representative char(6),
   last_maint_type char(1),
   last_maint_datetime date
)
"""
if not helperfunctions.executeSQL(create_Y03_last_mth):
    logging.error("Error in executing sql for creating R0252_Y03_last_mth table")
    sys.exit(101)

# Dropping R0252_CIBC_TRANSFER table
drop_CIBC_TRANSFER = """
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE R0252_CIBC_TRANSFER';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
"""
if not helperfunctions.executeSQL(drop_CIBC_TRANSFER):
    logging.error("Error in executing sql for dropping R0252_CIBC_TRANSFER table")
    sys.exit(101)

# Creating R0252_CIBC_TRANSFER table
create_CIBC_TRANSFER = """
CREATE TABLE R0252_CIBC_TRANSFER (
   FINS_NO varchar2(10),
   BROKER varchar2(40)
)
"""
if not helperfunctions.executeSQL(create_CIBC_TRANSFER):
    logging.error("Error in executing sql for creating R0252_CIBC_TRANSFER table")
    sys.exit(101)

# Dropping R0252_BLOTTER_CODE table
drop_BLOTTER_CODE = """
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE R0252_BLOTTER_CODE';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
"""
if not helperfunctions.executeSQL(drop_BLOTTER_CODE):
    logging.error("Error in executing sql for dropping R0252_BLOTTER_CODE table")
    sys.exit(101)

# Creating R0252_BLOTTER_CODE table
create_BLOTTER_CODE = """
CREATE TABLE R0252_BLOTTER_CODE (
   blotter varchar2(2)
)
"""
if not helperfunctions.executeSQL(create_BLOTTER_CODE):
    logging.error("Error in executing sql for creating R0252_BLOTTER_CODE table")
    sys.exit(101)

# Dropping R0252_TRACK1_TABLE table
drop_TRACK1_TABLE = """
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE R0252_TRACK1_TABLE';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
"""
if not helperfunctions.executeSQL(drop_TRACK1_TABLE):
    logging.error("Error in executing sql for dropping R0252_TRACK1_TABLE table")
    sys.exit(101)

# Creating R0252_TRACK1_TABLE table
create_TRACK1_TABLE = """
CREATE TABLE R0252_TRACK1_TABLE (
   driver varchar2(4),
   fn_code varchar2(20)
)
"""
if not helperfunctions.executeSQL(create_TRACK1_TABLE):
    logging.error("Error in executing sql for creating R0252_TRACK1_TABLE table")
    sys.exit(101)

# Dropping R0252_DRIVER_3040 table
drop_DRIVER_3040 = """
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE R0252_DRIVER_3040';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
"""
if not helperfunctions.executeSQL(drop_DRIVER_3040):
    logging.error("Error in executing sql for dropping R0252_DRIVER_3040 table")
    sys.exit(101)

# Creating R0252_DRIVER_3040 table
create_DRIVER_3040 = """
CREATE TABLE R0252_DRIVER_3040 (
   ds_sec_id varchar2(15)
)
"""
if not helperfunctions.executeSQL(create_DRIVER_3040):
    logging.error("Error in executing sql for creating R0252_DRIVER_3040 table")
    sys.exit(101)


# BCP operations
if not helperfunctions.importTableDataFromCSV(file_name_5, 'db_dev..R0252_Y03_last_mth', False, '^@'):
    logging.error("Error 103d BCP " + file_name_5 + " Failed")
    sys.exit(103)

# SQL operations for creating tables, indexes, and running stored procedures
 # Importing the helperfunctions module

# Setting up logging
logging.basicConfig(level=logging.INFO)

# Oracle statements converted to anonymous PL/SQL blocks and executed using helperfunctions.executeSQL

# ALTER SESSION SET CURRENT_SCHEMA
query_to_execute = """
BEGIN
    EXECUTE IMMEDIATE 'ALTER SESSION SET CURRENT_SCHEMA = :schema_name';
END;
"""
if not helperfunctions.executeSQL(query_to_execute, schema_name='db_dev'):
    logging.error("Error in executing ALTER SESSION SET CURRENT_SCHEMA")
    sys.exit(101)

# CREATE INDEX idx1 ON R0252_login_group
query_to_execute = """
BEGIN
    EXECUTE IMMEDIATE 'CREATE INDEX idx1 ON R0252_login_group (userid)';
END;
"""
if not helperfunctions.executeSQL(query_to_execute):
    logging.error("Error in executing CREATE INDEX idx1 ON R0252_login_group")
    sys.exit(101)

# CREATE INDEX idx2 ON R0252_login_channel
query_to_execute = """
BEGIN
    EXECUTE IMMEDIATE 'CREATE INDEX idx2 ON R0252_login_channel (userid)';
END;
"""
if not helperfunctions.executeSQL(query_to_execute):
    logging.error("Error in executing CREATE INDEX idx2 ON R0252_login_channel")
    sys.exit(101)

# Executing stored procedures
stored_procedures = [
    'af_R0252_Trades_sp(0)',
    'af_R0252_Trades_sp2(0)',
    'af_R0252_Track1(0)',
    'af_R0252_unique_logins',
    'af_R0252_Trades_details_sp(0)',
    'af_R0252_Trade_trailer',
    'af_R0252_commission_trailer(0)',
    'af_R0252_TRADE_TABLES_CLEANUP'
]

for sp in stored_procedures:
    query_to_execute = f"""
    BEGIN
        {sp};
    END;
    """
    if not helperfunctions.executeSQL(query_to_execute):
        logging.error(f"Error in executing {sp}")
        sys.exit(101)

logging.info("All Oracle statements executed successfully.")


# Check log file for errors
# This part needs to be replaced with Python code that checks the log file for specific errors
# For simplicity, we're using a placeholder function check_log_for_errors() which you need to implement
if helperfunctions.check_log_for_errors(LOG):
    logging.error("Error 105 on `date`")
    sys.exit(105)

# BCP out operations
if not helperfunctions.importTableDataFromCSV(file_name_5, 'db_dev..R0252_Y03_last_mth', False, '^@'):
    logging.error("Error 103d BCP " + file_name_5 + " Failed")
    sys.exit(103)

# Clean up
logging.info("11. delete unused files. `date`")
os.remove(COMMISSION_file)
os.remove(TRADES_file)

# SQL cleanup operations


# Importing necessary modules
logging.basicConfig(level=logging.INFO)

# Oracle PL/SQL block for executing stored procedure
plsql_block_af_R0252_TRADE_TABLES_CLEANUP = """
BEGIN
    db_dev.af_R0252_TRADE_TABLES_CLEANUP;
END;
"""

# Execute the PL/SQL block for af_R0252_TRADE_TABLES_CLEANUP
if not helperfunctions.executeSQL(plsql_block_af_R0252_TRADE_TABLES_CLEANUP):
    logging.error("Error in executing af_R0252_TRADE_TABLES_CLEANUP")
    sys.exit(101)
else:
    logging.info("Successfully executed af_R0252_TRADE_TABLES_CLEANUP")


logging.info("The Process {PROCESS} completed at `date`")


# This regenerated script now includes placeholders for all the specified comments, ensuring that the structure aligns more closely with the original Korn shell script. It's important to note that the actual implementation details for some operations (e.g., SQL execution, file copying) depend on the specific Python libraries and external systems involved, which are represented by placeholders in this script.