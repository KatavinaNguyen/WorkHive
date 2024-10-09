# WorkHive Employee Management System <img src="src/main/java/icon/honeycomb_656353.png" width="70">
A simple yet powerful console application that helps you manage employees, process payroll, and generate reports—all in one place.

## Features 
+ **Employee Management:** Search, update, and manage employee data with ease.
+ **Payroll Processing:** Calculate salaries and generate comprehensive reports.
+ **Reliable & Tested:** Thoroughly tested to ensure smooth operation.

## How to Install & Run
1. **Clone the Repo:** `git clone https://github.com/KatavinaNguyen/WorkHive.git`
2. ~~**Set Up the Database:** Install MySQL and set up the database.~~

> [!NOTE]
> We switched from MySQL to H2 to make the project easier for users to run out-of-the-box without needing to configure or connect to an external database, as H2 is an embedded, in-memory database that runs within the application.
> To switch back to MySQL, update the JDBC URL, username, and password in the code, and ensure the MySQL JDBC driver is included in the project dependencies.

3. **Run WorkHive:** Compile and run the application using your favorite Java IDE. 

## How to Use 
+ **Add Employees:** Easily input and manage employee details.
+ **Process Payroll:** Run payroll calculations and export reports.
+ **Generate Reports:** Create and review employee and payroll reports.
