#! /bin/ksh
#----------------------------------------------------------------------------
# program:     	M&M SI DATA FEED. ( COMMISSION - TRADE ) files 2 and 4
# request:      R0252
# created by :	Alex Finkelson
# date:        	unknown
# modified:	Nov 23, 2003 by Alex Finkelson for Mid Office Conversion
#		Jan. 24, 2005 by Samuel Tang for WG949B - add more drivers
# modified:	April 20, 2006, By Alex Finkelson for MOC Phase IV
# purpose:     	M&M SI DATA FEED.
# modified:	June 23, 2010, By Rakesh Arora for BT847-SFTP project
# 			1   To facilitate sftp, regular non-secure ftp code has been
#           	removed with copy output files  to ${sftp_out_lz_nas}
#			2	Addition of a tag for future header/trailer verification and removal
#			3.  Removed Hardcoded IP.s	
# modified:	February 3, 2011, By Stacey Rocheleau-Bourgeois for BT847-SFTP project
#			1  Add header/trailer verification for input files
# modified:     April 25, 2011, By Amyn Lalani for BT847-SFTP project
#               Copied COMMISSION.TXT AND EXPENSE_ACTIVITIES.TXT file to shared_folder as well.
# modified:     Nov 25, 2011, By Amyn Lalani for BT2011-015 project.
#               Execute script R0252_copy_Informatica.ksh for copying login and R0304 files to Informatica out folder.
#----------------------------------------------------------------------------
# environment setting
#----------------------------------------------------------------------------
#
#  /opt/work_12/reports/moc/reports/isi/R0252/R0252_TRADE.ksh
#
#  c:\_requests\consolidation\phace_4\group 2\r0252_trades_1048
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

LOG=$AST_SCRIPTS/R0252/R0252_TRADE.lis
MSG=$AST_SCRIPTS/R0252/R0252_TRADE.msg


#############################################################################################

#Create log files
echo "Start $PROCESS on `date`" > $LOG
echo "Start $PROCESS on `date`" > $MSG

echo "" >>$LOG

echo "Server :  $DSQUERY" >> $LOG

#############################################################################################

echo "ON INITIAL RUN EXECUTE THE DDL SCRIPT: 1048_DDL.ksh on `date`" >> $LOG
#############################################################################################################

if [ -f "1048_DDL.lis" ]
then
echo "DDL log exists, checking for errors at `date` \n" >> $LOG
$CSVRPTDIR/check_error.ksh 1048_DDL.lis

if (( $? ))
then 
echo "Errors found, re-running DDL SQL at `date` \n" >> $LOG


#################################
# one-time DDL execution, comment out after 1st run
#################################
$CURDIR/1048_DDL.ksh

# Check for successful execution of SQL stored procedure
if (( $? )) 
then
echo "Error creating stored procedure at `date` \n" >> $LOG
exit 102
else
echo "Created stored procedure at `date` \n" >> $LOG
fi

$CSVRPTDIR/check_error.ksh 1048_DDL.lis

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
$CURDIR/1048_DDL.ksh

# Check for successful execution of SQL stored procedure
if (( $? )) 
then
echo "Error creating stored procedure at `date` \n" >> $LOG
exit 102
else
echo "Created stored procedure at `date` \n" >> $LOG
fi

$CSVRPTDIR/check_error.ksh 1048_DDL.lis

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

########## Set local variables ##########



COMMISSION_file=R0252_RS_COMMISSION.TXT
TRADES_file=R0252_RS_TRADES.TXT

file_name_1=CIBC_TRANSFER.csv
file_name_2=BLOTTER_CODE.csv
file_name_3=TRACK1_TABLE.csv
file_name_4=DRIVER_3040.csv
file_name_5=Y03_last_mth.csv


month=`date +%m`
year=`date +%Y`
lastmonth=`expr $month - 1`
 
if [ $lastmonth == 0 ] ; then
lastmonth="12"
year=`expr $year - 1`
fi
 
if [ $lastmonth -le 9 ] ; then
lastmonth=0$lastmonth
fi
 
####### month before last month for Y03_last_mth.csv
prevmth2=`expr $lastmonth - 1`
 
if [ $prevmth2 == 0 ] ; then
prevmth2="12"
fi
 
if [ $prevmth2 -le 9 ] ; then
prevmth2=0$prevmth2
fi

#########################################################################################################
## BT847 SFTP on Jun 23, 2010 by Rakesh Arora ** Replaced ftp by cp and destination accordingly **Start


echo "1. GET TRACK 1 FILES. `date`" >> $LOG

for reportname in $file_name_1 $file_name_2 $file_name_3 $file_name_4 $file_name_5 
do

	echo "Copying file $reportname from ${sftp_in} folder on  `date`" >> $LOG

	cp  ${sftp_in}/$reportname ./$reportname  >> $LOG 2>&1
	if (( $? )) 
	then
		echo "Error: ${err_ksh}, file copy $reportname failed in dir $CURDIR  on `date`" >> $LOG
		exit $err_ksh
	fi
done

#  tag_rem_hdr_trl -  Place holder to Remove header directly after copying the file 


for filename in login_channel.txt$year$lastmonth login_group.txt$year$lastmonth 
do
	echo "Processing input file ${sftp_in}/${filename}"		>> $LOG
	
	# Get the current and previous header file names
	file_start=$(echo ${filename} | awk -F"." '{ print $1; }')
	curr_header=${file_start}"_curr_header"
	prev_header=${file_start}"_prev_header"
	

	# Populate current header file and verify header is set.
	head -1 ${sftp_in}/${filename} | grep 'HEADER' > ${curr_header}

        if [ ! -s ${curr_header} ]; then
                echo "\t** ERROR: Header record missing from ${filename} ...exiting \n" >> $LOG
                exit ${err_ksh}
        fi
        printf  "\tHeader record found in ${filename} ...\n" >> $LOG

	# Check for previous run's header file.
  	if [ ! -f ${prev_header} ]; then
                echo "\t** ERROR: Header file from previous run missing: ${prev_header} ...exiting\n" >> $LOG
                exit ${err_ksh}
        fi
         
        # Get the current and previous file creation dates in the header
        prev_header_date=$( cat ${prev_header} | awk -F"-" '{ print $2 }' )
        curr_header_date=$( cat ${curr_header} | awk -F"-" '{ print $2 }' )

        # Compare header dates and check that the current run is different from the previous run
        if [ "${curr_header_date}" -eq "${prev_header_date}" ]; then
                echo "\t** ERROR: This header date same as last file header date in ${prev_header}" >> $LOG
                echo "\t\tDate: ${prev_header_date} ... exiting\n" 	>>$LOG
                exit ${err_ksh}
        fi
        
        echo "\tHeader date verified and the current input file differs from the previous run." >> $LOG


	# Verify trailer exists	
	trailer=$(tail -1 ${sftp_in}/${filename} | grep 'TRAILER')
	
	if [ -z "${trailer}" ];
	then
		echo "\t** ERROR: Trailer record missing from ${filename}."	>> $LOG			
		exit ${err_ksh}
	fi



	echo "\tStrip header and trailer from ${filename} and copy it to the current directory." >> $LOG

        sed '1d;$d' ${sftp_in}/${filename} > ./${filename}		
	
	if (( $? )); then
		echo "\t** ERROR: Failed to create ${filename}!"	>> $LOG
		exit ${err_ksh}
	fi
	
	echo "${filename} successfully created. $(date)"		>> $LOG
	
	

	# Compare the trailer record count against the actual record count
	trailer_rec=$(echo ${trailer} | awk '{ FS="-"; } { print $2; }')
	file_rec=$(wc -l ${filename} | awk '{ print $1; }')
	
	echo "\tActual record count: ${file_rec}\n\tTrailer record count: ${trailer_rec}\n" >> $LOG
	
	if [ ${trailer_rec} -ne ${file_rec} ]; then
		echo "\t** ERROR: Number of records ${file_rec} does not match trailer record count ${trailer_rec}" >> $LOG
		echo "\tTrailer: ${trailer}"						>> $LOG
		exit ${err_ksh}
	fi

	R0252_copy_Informatica.ksh ${year} ${lastmonth}

	if (( $? )); then
		printf  "** -   ERROR could not copy files for <%s> year and <%s> month to Informatica out folder, copy failed ....\n"  \
		${year} ${lastmonth} >> $LOG
		printf  "** - see log for R0252_copy_Informatica." >> $LOG
		exit ${err_ksh}
	fi
	printf  "   -   successful copy of files for <%s> year and <%s> month to Informatica out folder ....\n"   \
	${year} ${lastmonth} >> $LOG

done



#########################################################################################################
#
echo "2. CREATE TEMP. TABLES ON THE $db_dev DATABASE. `date`" >> $LOG

isql -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY -e<<%EOF >> $LOG

GO
SET clientapplname $PROCESS
GO

use $db_dev
go

IF EXISTS(SELECT name FROM sysobjects 
          WHERE type = "U" AND name = "R0252_login_channel")
   BEGIN 
        drop table R0252_login_channel

  END
go
   
create table R0252_login_channel (
	userid varchar(40) null,
	logins varchar(40) null,
	channel varchar(40) null,
	user_group varchar(100) null )
go


IF EXISTS(SELECT name FROM sysobjects 
          WHERE type = "U" AND name = "R0252_login_group")
   BEGIN 
        drop table R0252_login_group

  END

go
   
create table R0252_login_group (
        userid varchar(40) null,
	org_id varchar(40) null,
	user_group varchar(40) null,
	account_id varchar(100) null )
go

IF EXISTS(SELECT name FROM sysobjects 
          WHERE type = "U" AND name = "R0252_Y03_last_mth")
   BEGIN 
        drop table R0252_Y03_last_mth

  END

go

CREATE TABLE R0252_Y03_last_mth
( client                  CHAR     (20) NULL, 
  representative          CHAR     (6) NULL, 
  last_maint_type         char     (1) NULL,
  last_maint_datetime     datetime null)                                              
go

IF EXISTS(SELECT name FROM sysobjects 
          WHERE type = "U" AND name = "R0252_CIBC_TRANSFER")
   BEGIN 
        drop table R0252_CIBC_TRANSFER
  END

go
CREATE TABLE $db_dev..R0252_CIBC_TRANSFER
( FINS_NO                VARCHAR     (10) NULL, 
  BROKER                 VARCHAR     (40) NULL) 
                                    
go

IF EXISTS(SELECT name FROM sysobjects 
          WHERE type = "U" AND name = "R0252_BLOTTER_CODE")
   BEGIN 
        drop table R0252_BLOTTER_CODE
  END

go

CREATE TABLE $db_dev..R0252_BLOTTER_CODE
( blotter            VARCHAR     (2) NULL) 
                                     
go

IF EXISTS(SELECT name FROM sysobjects 
          WHERE type = "U" AND name = "R0252_TRACK1_TABLE")
   BEGIN 
        drop table R0252_TRACK1_TABLE
  END

go

CREATE TABLE $db_dev..R0252_TRACK1_TABLE
( driver            VARCHAR     (4) NULL,
   fn_code	    varchar	(20) NULL) 
                                     
go

IF EXISTS(SELECT name FROM sysobjects 
          WHERE type = "U" AND name = "R0252_DRIVER_3040")
   BEGIN 
        drop table R0252_DRIVER_3040
  END

go


CREATE TABLE $db_dev..R0252_DRIVER_3040
( ds_sec_id    VARCHAR     (15) NULL) 
                                     
go

%EOF

if (( $? )); then 
	echo "Error 101 failed to run SQL on `date`\n" >> $LOG
	exit 101
fi
        echo "The SQL run successfully on `date`\n" >> $LOG


#########################################################################################################

   
echo "3. BCP IN THE FILE INTO THE TABLE R0252_login_channel. `date`" >> $LOG

bcp $db_dev..R0252_login_channel in login_channel.txt$year$lastmonth -c -t '|' -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY >> $LOG

if [ $? -ne 0 ] 
then
   	echo "Error 103a BCP R0252_login_channel Failed " >> $LOG
	exit 103
fi

        echo "bcp R0252_login_channel worked" >> $LOG

#########################################################################################################



echo "4. BCP IN THE FILE INTO THE TABLE R0252_login_group. `date`" >> $LOG

bcp $db_dev..R0252_login_group in login_group.txt$year$lastmonth -c -t '|' -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY >> $LOG

if [ $? -ne 0 ] 
then
   	echo "Error 103c BCP R0252_login_group Failed " >> $LOG
	exit 103
fi

        echo "bcp R0252_login_group worked" >> $LOG

#########################################################################################################



############ WG949B - bcp in $file_name_5  for checking closed accounts

echo "5. bcp Y03_last_mth.csv into $db_dev **" >> $LOG

bcp $db_dev..R0252_Y03_last_mth in $file_name_5 -c -t '^@' -Jiso_1 -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY >> $LOG

if [ $? -ne 0 ] 
then
   	echo "Error 103d BCP $file_name_5 Failed " >> $LOG
	exit 103
fi

        echo "bcp $file_name_5 worked" >> $LOG
        
        

#########################################################################################################
echo "5.1 bcp CIBC_TRANSFER.csv into $db_dev **" >> $LOG

bcp $db_dev..R0252_CIBC_TRANSFER in $file_name_1 -c -t ',' -Jiso_1 -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY >> $LOG

if [ $? -ne 0 ] 
then
   	echo "Error 103d BCP R0252_CIBC_TRANSFER Failed " >> $LOG
	exit 103
fi

        echo "bcp R0252_CIBC_TRANSFER worked" >> $LOG


#########################################################################################################

echo "5.2 bcp BLOTTER_CODE.csv into $db_dev **" >> $LOG

bcp $db_dev..R0252_BLOTTER_CODE in $file_name_2 -c -t ',' -Jiso_1 -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY >> $LOG

if [ $? -ne 0 ] 
then
   	echo "Error 103e BCP R0252_BLOTTER_CODE Failed " >> $LOG
	exit 103
fi

        echo "bcp R0252_BLOTTER_CODE worked" >> $LOG
        


#########################################################################################################

echo "5.3 bcp TRACK1_TABLE.csv into $db_dev **" >> $LOG
bcp $db_dev..R0252_TRACK1_TABLE in $file_name_3 -c -t ',' -Jiso_1 -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY >> $LOG

if [ $? -ne 0 ] 
then
   	echo "Error 103d BCP R0252_TRACK1_TABLE Failed " >> $LOG
	exit 103
fi

        echo "bcp R0252_TRACK1_TABLE worked" >> $LOG
#########################################################################################################

echo "5.4 bcp DRIVER_3040.csv into $db_dev **" >> $LOG

bcp $db_dev..R0252_DRIVER_3040 in $file_name_4 -c -t ',' -Jiso_1 -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY >> $LOG

if [ $? -ne 0 ] 
then
   	echo "Error 103e BCP R0252_DRIVER_3040 Failed " >> $LOG
	exit 103
fi

        echo "bcp R0252_DRIVER_3040 worked" >> $LOG
#########################################################################################################


############## end of change WG949B ################################################

echo "6. ** CREATE TABLES indexes and run sp ** `date`" >> $LOG

isql -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY -e<<%EOF >> $LOG

GO
SET clientapplname $PROCESS
GO

use $db_dev
go

create index idx1 on R0252_login_group  (userid)
go

create index idx2 on R0252_login_channel (userid)
go

--------------------------------------------------------------------
exec af_R0252_Trades_sp 0
go

--------------------------------------------------------------------

---- WG949B by Samuel Tang  on Jan. 24, 2005
exec af_R0252_Trades_sp2 0
go

--------------------------------------------------------------------
exec af_R0252_Track1 0
go

--------------------------------------------------------------------

exec af_R0252_unique_logins
go

--------------------------------------------------------------------
---- WG949B by Samuel Tang  on Jan. 24, 2005 , footer has been taken out to af_R0252_Trade_trailer
exec af_R0252_Trades_details_sp 0
go

exec af_R0252_Trade_trailer
go

--------------------------------------------------------------------

---- WG0949B by Samuel Tang on March 2, 2005 , replace af_R0252_commition_footer_sp
---- driver 2001 also in commission file
exec af_R0252_commission_trailer 0
go

--------------------------------------------------------------------
exec af_R0252_TRADE_TABLES_CLEANUP
go

--------------------------------------------------------------------
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

####################################################################################################


echo "7. BCP RESULT SET TABLES IN THE FILE. `date`" >> $LOG

bcp $db_dev..R0252_Y03_this_mth out $file_name_5 -c -t '^@' -Jiso_1 -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY >> $LOG

if [ $? -ne 0 ] 
then
   	echo "Error 103a BCP R0252_Y03_this_mth Failed " >> $LOG
	exit 103
fi

        echo "bcp R0252_Y03_this_mth worked" >> $LOG

###########################################################################################################

echo "8. BCP R0252_RS_COMMISSION TABLES OUT IN THE FILE. `date`" >> $LOG

bcp $db_dev..R0252_RS_COMMISSION out $COMMISSION_file -c -t , -Jiso_1 -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY >> $LOG

if [ $? -ne 0 ] 
then
   	echo "Error 103b BCP R0252_RS_COMMISSION Failed " >> $LOG
	exit 103
fi

        echo "bcp R0252_RS_COMMISSION worked" >> $LOG

###########################################################################################################

echo "9. BCP RR0252_RS_TRADES OUT IN THE FILE. `date`" >> $LOG

bcp $db_dev..R0252_RS_TRADES out $TRADES_file -c -t , -Jiso_1 -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY >> $LOG

if [ $? -ne 0 ] 
then
   	echo "Error 103c BCP R0252_RS_TRADES Failed " >> $LOG
	exit 103
fi

        echo "bcp R0252_RS_TRADES worked" >> $LOG

###########################################################################################################

echo  "8. Get rid of the position 81 in the file.  `date`" >> $LOG

cut -c 1-80 $COMMISSION_file   > COMMISSION.TXT 
cut -c 1-80 $TRADES_file       > EXPENSE_ACTIVITIES.TXT 

###########################################################################################################

echo "Copying THE RESULT SET FILE TO ${sftp_out_lz_nas} FORLDER/SERVER ON `date`" >> $LOG

for reportname in COMMISSION.TXT EXPENSE_ACTIVITIES.TXT $file_name_5 
do
        echo "Copying THE RESULT SET FILE $reportname TO ${sftp_out_lz_nas} FOLDER ON `date`" >> $LOG

        cp $AST_SCRIPTS/R0252/$reportname ${sftp_out_lz_nas}/$reportname  >> $LOG 2>&1
        if (( $? ))
        then
                echo "Error: file copy $reportname failed in sftp in dir ${sftp_out_lz_nas} on `date`" >> $LOG
                exit $err_ksh
        fi

        if [ "${reportname}" == "COMMISSION.TXT" ] || [ "${reportname}" ==  "EXPENSE_ACTIVITIES.TXT" ]; then
                echo "Copying THE RESULT SET FILE $reportname TO ${shared_files} FOLDER ON `date`" >> $LOG

                cp $AST_SCRIPTS/R0252/${reportname} ${shared_files}/${reportname}  >> $LOG 2>&1
                if (( $? ))
                then
                        echo "Error: file copy $reportname failed into dir ${shared_files} on `date`" >> $LOG
                        exit $err_ksh
                fi
        fi
done

#########################################################################################################

echo "11. delete unused files. `date`" >> $LOG

rm $COMMISSION_file
rm $TRADES_file

rm login_channel.txt$year$lastmonth
rm login_group.txt$year$lastmonth

mv login_channel_curr_header login_channel_prev_header	>> $LOG 2>&1
if (( $? )); then
	echo "Error moving login_channel_curr_header to login_channel_prev_header" >> $LOG
	exit ${err_ksh}
fi

mv login_group_curr_header login_group_prev_header	>> $LOG 2>&1
if (( $? )); then
	echo "Error moving login_group_curr_header to login_group_prev_header" >> $LOG
	exit ${err_ksh}
fi

rm  $file_name_1
rm  $file_name_2
rm  $file_name_3
rm  $file_name_4

#########################################################################################################

echo "12. ********** CLEAN UP THE RESULT SET TABLES.  **********************  `date` " >> $LOG


isql -U$AST_DBUSER -P`cat $PASSWDFILE` -S$DSQUERY -e<<%EOF >> $LOG

GO
SET clientapplname $PROCESS
GO

use $db_dev
go


exec af_R0252_TRADE_TABLES_CLEANUP
go

go

%EOF

if (( $? )); then 
	echo "Error 101 failed to run SQL on `date`\n" >> $LOG
	exit 101
fi
        echo "The SQL run successfully on `date`\n" >> $LOG



###############################################################################

echo " " >> $LOG
echo "**********************************************************************" >> $LOG
echo "The Process $PROCESS completed at `date`" >> $LOG
echo "The Process $PROCESS completed at `date`" >> $MSG
echo "**********************************************************************" >> $LOG

#  tag_mv_header_rec_file - directly before successfully exiting the process

exit 0

###############################################################################

