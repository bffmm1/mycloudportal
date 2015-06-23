# Introduction #

MyCloudPortal is a simple java web application using Spring & Hibernate. This should work in any J2EE container. It has been tested on Tomcat 6.x and Mysql 5.x . This document explains the installation process of mycloudportal.

**Apache Tomcat 6.x**

Download and install Apache Tomcat 6.x from http://tomcat.apache.org/download-60.cgi and http://tomcat.apache.org/tomcat-6.0-doc/setup.html

**MySql 5.x installation**

Download and install MySql 5.x from http://www.mysql.com/downloads/mysql/ and http://dev.mysql.com/doc/refman/5.5/en/installing.html

**Make sure your mysql installation is case insensitive by adding the below line into my.cnf file**

`lower_case_table_names= 1`


**Download Mycloudportal**

Download the web app from http://code.google.com/p/mycloudportal/downloads/list

**Setup Mycloudportal**

> Unzip the web application into ROOT folder in your tomcat ‘webapps’ folder.

**Setup mycp database**

> Create a schema named ‘mycp’ in your mysql installation.
> Execute the mycp.ddl.sql first and then the mycp.dml.sql .
> Test and make sure the schema is created properly in mysql.

**Edit configuration files**

Edit ROOT\WEB-INF\classes\jbpm.hibernate.cfg.xml and change the DB username,password and schema details.
Edit ROOT\WEB-INF\classes\META-INF\spring\database.properties , and do the same.

If you want email integration , you can setup your gmail account in ROOT\WEB-INF\classes\META-INF\spring\email.properties.

**Deploy & start mycloudportal**

Start tomcat and check out catalina.out for any error or success logs.
After successful deploy, go to your browser and enter http://localhost:<your port>
The initial setup of mycloudportal comes with a default  super admin account. This gives you complete access across any account in the locally installed mycloudportal.
> superadmin@mycloudportal.in /password
You can use it to login and go through the application or you can signup in the login page to create a new account.