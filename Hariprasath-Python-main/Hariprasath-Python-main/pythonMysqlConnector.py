import mysql.connector
import csv
mydb = mysql.connector.connect(
  host="localhost",
  user="root",
  password="root",
  database="mycustomer"
)
mycursor=mydb.cursor()
# mycursor.execute(" Database mycustomer")
# CreateCustomerDetail="create table customer(customer_id int,name varchar(255),purchase int);"
# mycursor.execute(CreateCustomerDetail)
# insertCustomerValues="INSERT INTO customer (customer_id,name,purchase ) VALUES (%s, %s,%s)"
# val=[(1, 'hari', 300),(2, 'logesh', 400)]
# mydb.commit()
# mycursor.executemany(insertCustomerValues,val)
mydb.commit()
sql="Select * From customer"
sql1="Select * From customer ORDER BY customer_id"
sql2="update  customer set purchase=1000 where purchase=400"
mycursor.execute(sql)
myresult=mycursor.fetchall()

with open("mydata.txt",'w',newline='') as file:
    writer=csv.writer(file)
    for x in myresult:
        writer.writerow(x)

