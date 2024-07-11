#! /bin/ksh
#----------------------------------------------------------------------------
# program:     	M&M SI DATA FEED. -- MF.KSH (FILE #3)
# request:      R0252
# created by :	Alex Finkelson
# date:        	unknown
# modified:	October 29, 2003 by Alex Finkelson for Mid Office Conversion
# modified:	April 18, 2006, By Alex Finkelson for MOC Phase IV
# modified:	June 22, 2010, By Rakesh Arora for BT847-SFTP project
# 			1   To facilitate sftp, regular non-secure ftp code has been
#           	removed with copy output files  to ${sftp_out_lz_nas}
#			2	Addition of a tag for future header/trailer verification and removal
#               2004/01/18 WG949B - add drivers for equity (Samuel Tang)
# modified:     April 25, 2011, By Amyn Lalani for BT847-SFTP project
#               Copied MF_BALANCES.TXT file to shared_folder as well.
# purpose:     	M&M SI DATA FEED.
#----------------------------------------------------------------------------
# environment setting
#----------------------------------------------------------------------------
#
#  /opt/work_12/reports/moc/reports/isi/R0252/R0252_MF.ksh
#
#  c:\_requests\consolidation\phace_4\group 2\r0252_mf_1047
#  
#  /opt/work_12/reports/isi/R0252   - ISI PROD LOCATION
#
#----------------------------------------------------------------------------
cd `dirname $0`
CURDIR=$PWD
WGDIR=`dirname $CURDIR`
REPTDIR=`dirname $WGDIR`
AST_SCRIPTS=$WGDIR
 
export CURDIR
export WGDIR
export REPTDIR
export AST_SCRIPTS 
 
. $WGDIR/rptProfile

############################     Set local variables    #####################################

#Process name variable
PROCESS=`basename $0 | cut -f1 -d'.'`

LOG=$AST_SCRIPTS/R0252/R0252_MF.lis
MSG=$AST_SCRIPTS/R0252/R0252_MF.msg


#############################################################################################

#Create log files
echo "Start $PROCESS on `date`" > $LOG
echo "Start $PROCESS on `date`" > $MSG

echo "" >>$LOG

echo "Server :  $DSQUERY" >> $LOG


#############################################################################################

echo "ON INITIAL RUN EXECUTE THE DDL SCRIPT: 1047_DDL.ksh on `date`" >> $LOG

#############################################################################################################

if [ -f "1047_DDL.lis" ]
then
echo "DDL log exists, checking for errors at `date` \n" >> $LOG
$CSVRPTDIR/check_error.ksh 1047_DDL.lis

if (( $? ))
then 
echo "Errors found, re-running DDL SQL at `date` \n" >> $LOG


#################################
# one-time DDL execution, comment out after 1st run
#################################
$CURDIR/1047_DDL.ksh

# Check for successful execution of SQL stored procedure
if (( $? )) 
then
echo "Error creating stored procedure at `date` \n" >> $LOG
exit 102
else
echo "Created stored procedure at `date` \n" >> $LOG
fi

$CSVRPTDIR/check_error.ksh 1047_DDL.lis

if (( $? ))
then 
echo "Error Code 101 - SQL errors found in DDL log at `date` \n" >> $LOG
exit 101
else
echo "Stored procedure completed successfully at `date` \n" >> $LOG
fi

else
echo "Stored procedure already run, skipping at `date` \n" >> $LOG
fi

else
#################################
# one-time DDL execution, comment out after 1st run
#################################
$CURDIR/1047_DDL.ksh

# Check for successful execution of SQL stored procedure
if (( $? )) 
then
echo "Error creating stored procedure at `date` \n" >> $LOG
exit 102
else
echo "Created stored procedure at `date` \n" >> $LOG
fi

$CSVRPTDIR/check_error.ksh 1047_DDL.lis

if (( $? ))
then 
echo "Error Code 101 - SQL errors found in DDL log at `date` \n" >> $LOG
exit 101
else
echo "Stored procedure completed successfully at `date` \n" >> $LOG
fi
fi

#############################################################################################################


###########################################################################################
#                                                                                         #
#                                 R0252                                                   #
#                                                                                         #
###########################################################################################


MF_file=R0252_RS_MF.TXT
file_name=CIBC_FUND.csv
RS_file=MF_BALANCES.TXT


echo "1.1 Get CIBC_FUND.csv tables(for future use - change driver 1301-1404 by table driven) on `date`" >> $LOG

# get file parm.

## BT847 SFTP on Jun 22, 2010 by Rakesh Arora ** Replaced ftp by cp and destination accordingly **Start

echo "Copying file $file_name from the $sftp_in dir on `date` " >> $LOG
cp  ${sftp_in}/${file_name} ./${file_name}  >> $LOG 2>&1
if (( $? )) 
then
	echo "Error: ${err_ksh}a, file copy $file_name failed in dir $CURDIR  on `date`" >> $LOG
	exit $err_ksh
fi


#  tag_rem_hdr_trl -  Place holder to Remove header directly after copying the file 

echo "1.2 Create CIBCDEV..CIBC_FUND   `date`" >> $LOG


isql -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY -e<<%EOF >> $LOG

GO
SET clientapplname $PROCESS
GO

use $db_dev
go

IF EXISTS(SELECT name FROM sysobjects  WHERE type = "U" AND name = "CIBC_FUND")
        drop table CIBC_FUND
go

CREATE TABLE CIBC_FUND
( symbol        varchar  (6) NULL, 
  ti_desc_1     varchar  (50) NULL, 
  MF_Type       varchar  (25) NULL,
  ti		int	      null,
  driver	char	 (4) null)                                              
go

%EOF

if (( $? )); then 
	echo "Error 101 failed to CREATE TABLE CIBC_FUND on `date`\n" >> $LOG
	exit 101
fi
        echo "The CREATE TABLE CIBC_FUND run successfully on `date`\n" >> $LOG


###########################################################################################################


echo "BCP CIBC_FUND table in on `date`" >> $LOG

bcp $db_dev..CIBC_FUND in ./$file_name -c -t , -Jiso_1 -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY >> $LOG

if [ $? -ne 0 ] 
then
   	echo "Error 103a BCP CIBC_FUND Failed " >> $LOG
	exit 103
fi

        echo "bcp CIBC_FUND worked" >> $LOG

###########################################################################################################

echo "2. RUN SP af_R0252_MF/af_R0252_EQ/af_R0252_MF_trailer   `date`" >> $LOG

isql -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY -e<<%EOF >> $LOG

GO
SET clientapplname $PROCESS
GO

use $db_dev
go

-- Trailer part has been taken out from following sp (WG949B, Samuel Tang 2004/01/17)

exec af_R0252_MF 0 
go

-- Run for Equity except MF (WG949B, Samuel Tang  2004/01/17)
exec af_R0252_EQ_NEW
go

-- Create trailer record (WG949B, Samuel Tang 2004/01/17)
exec af_R0252_MF_trailer
go

go


%EOF

if (( $? )); then 
	echo "Error 101b failed to execute SQL on `date`\n" >> $LOG
	exit 101
fi
        echo "The SQL executed successfully on `date`\n" >> $LOG


# Check log file for error messages
echo "Checking LOG file for errors on `date`" >> $LOG
echo "" >>$LOG
$CSVRPTDIR/check_error.ksh $LOG 
if (( $? ))
then 
echo "Error 105 on `date`" >> $LOG
echo >> $LOG
exit 105
fi


###########################################################################################################


echo "BCP R0252_RS_MF table in on `date`" >> $LOG

bcp $db_dev..R0252_RS_MF out ./$MF_file -c -t , -Jiso_1 -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY >> $LOG

if [ $? -ne 0 ] 
then
   	echo "Error 103a BCP R0252_RS_MF Failed " >> $LOG
	exit 103
fi

        echo "bcp R0252_RS_MF worked" >> $LOG

###########################################################################################################


echo  "3. Get rid of the position 81 in the file.  `date`" >> $LOG

cut -c 1-80 $MF_file  > $RS_file

echo "4. Copy THE FILES INTO THE ${sftp_out_lz_nas} folder. `date`" >> $LOG

#	tag_add_hdr_trl   - Addition of Header / Trailer to outgoing files

cp $AST_SCRIPTS/R0252/$RS_file ${sftp_out_lz_nas}/$RS_file >> $LOG 2>&1	

if (( $? )) 
then
	echo "Error ${err_ksh}b, Failed to copy file $RS_file to ${sftp_out_lz_nas} folder on `date`" >> $LOG
	exit ${err_ksh}
fi

echo "5. Copy THE FILES INTO THE ${shared_files} folder. `date`" >> $LOG

#	tag_add_hdr_trl   - Addition of Header / Trailer to outgoing files

cp $AST_SCRIPTS/R0252/$RS_file ${shared_files}/$RS_file >> $LOG 2>&1	

if (( $? )) 
then
	echo "Error ${err_ksh}b, Failed to copy file $RS_file to ${shared_files} folder on `date`" >> $LOG
	exit ${err_ksh}
fi
###########################################################################################################

echo "7. Delete file MF_BALANCES.TXT.Z . `date`" >> $LOG

rm $MF_file
rm $file_name
rm $RS_file

###############################################################################

echo " " >> $LOG
echo "**********************************************************************" >> $LOG
echo "The Process $PROCESS completed at `date`" >> $LOG
echo "The Process $PROCESS completed at `date`" >> $MSG
echo "**********************************************************************" >> $LOG

#  tag_mv_header_rec_file - directly before successfully exiting the process

exit 0

###############################################################################


