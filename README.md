# Revature-Project1
# Expense Reimbursement System (ERS) - Java CDE Full Stack

## Project Description
Expense Reimbursement System (ERS)<br> manage the process of reimbursing
employees for expenses incurred while on company time. All employees in the
company can log in and submit requests for reimbursement and view their past tickets
and pending requests. Finance managers can log in and view all reimbursement
requests and past history for all employees in the company. Finance managers are
authorized to approve and deny requests for expense reimbursement.Also finance manager can log in as an employee and
submit his own request.
## Technologies Used
- Java
- PostgreSQL
- JDBC
- HTML
- CSS
- JavaScript
- Log4J

## Features

- USER can log in as employee or manger if his account is a manager account
  <br><br>
- Employee account:
  - Login as an employee
  - View his old requests details and uploaded images
  - Filter old requests by request status
  - Create new request and upload multiple images with his request
  - Update his account information
    <br><br>
- Manager account:
    - Login as an employee & manager
    - View all requests details and uploaded images
    - Filter all requests by request status & users 
    - Create new request and upload multiple images with his request if he logged in as employee 
    - Update his account information if he logged in as employee
    - Create new user account with email notification features.
    - approve or deny requests with email notification features.
  
##ScreenShot 
###login page
![Reimbursement System login page](https://user-images.githubusercontent.com/12229049/115836334-f98f9000-a3dc-11eb-9991-f08d003e0ef5.JPG?raw=true)
###employee home
![Reimbursement System employee home](https://user-images.githubusercontent.com/12229049/115836664-5ee38100-a3dd-11eb-981a-3e4e534e27bf.JPG?raw=true)
###employee new request
![Reimbursement System employee new request](https://user-images.githubusercontent.com/12229049/115836816-8cc8c580-a3dd-11eb-8bbf-732fe6c5dbdf.JPG?raw=true)
###employee profile update
![Reimbursement System employee profile](https://user-images.githubusercontent.com/12229049/115836965-b2ee6580-a3dd-11eb-970c-9301bb034549.JPG?raw=true)
###employee profile with password update
![Reimbursement System employee profile with password](https://user-images.githubusercontent.com/12229049/115837295-095ba400-a3de-11eb-9c98-e8ce2c08c45d.JPG?raw=true)
## Getting Started
#### First, clone the project
- git clone https://github.com/SulimanSam/Revature-Project1.git
<br><br>
#### Second, set up the database
1. Open DBeaver (install if not installed) and connect to AWS RDS database
2. In the Database Manager, right-click the connection and select "Create" then "Database"
3. Set "Tablespace" to Default and give the database a name
4. Execute SQL Script in project1.sql file inside DBeaver => filepath in my repo Revature-Project1/project1/project1.sql to create all the tables
<br><br>
#### Third, set up the environment variables
1. In environment variables, create a variable "P1_DB_NAME" and set its value to be the name of the database that was created in the previous point
2. create a variable "DB_ENDPOINT" and set its value to be the endpoint of the RDS database that was created in AWS **add / at the end**
3. create a variable "DB_USERNAME" and set its value to be the username of the RDS database that was created in AWS
4. create a variable "DB_PASSWORD" and set its value to be the password of the RDS database that was created in AWS
5. create a variable "P1_EMAILING_ADDRESS" and set its value to be the address of an email that is set up to be used for sending emails to others **change the security options in the email** 
6. create a variable "P1_EMAILING_PASSWORD" and set its value to be the password for the previous email
<br><br>
#### Fourth, open the project in an IDE (preferably IntelliJ 2020.3 or above)
- Open IntelliJ
- Click File -> Open
- Go to where the repository was cloned
- Select the file then the Project1 file
- Run Main.java 
<br><br>

#### Fifth, open the login page
- in your browser use localhost:9001 to open the project

## Usage
1. On the login page (at localhost:9001, or the "ip address":"port number" if it was deployed, or the port was changed in all endpoints), if you Execute the SQL Script file the username will be **suliman** and password **1234** account type manager account.
2. login as manager and create new user you will receive an email with your login information.
3. login as employee with the new account to add new request 
4. Also, you can update your profile information 
5. If you logged back as manager you will find the new request you can either approve or deny that request you will receive an email.

## Contributing
Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are greatly appreciated.

1. Fork the Project
2. Create your Feature Branch (```git checkout -b feature/AmazingFeature```)
3. Commit your Changes (```git commit -m 'Add some AmazingFeature'```)
4. Push to the Branch (```git push origin feature/AmazingFeature```)
5. Open a Pull Request

## Contact
Suliman Sam - saimon.91@hotmail.com

