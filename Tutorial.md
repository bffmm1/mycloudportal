

# About #

My Cloud portal is an open source self service layer for the cloud. The current beta version allows you to connect and manage Eucalyptus 2.x, 3 private cloud & AWS public cloud along with your enterprise’s needs such as
  * Account, Department, Project and User management.
  * Asset (Compute, Volume etc) access is controlled at all the above 4 levels.
  * Multi cloud configuration. You can configure more than one eucalyptus or AWS cloud accounts, any number of products based on these  clouds and price it.
  * Can be deployed both as a hosted model and as a on-premise installation.
  * Manage the lifecycle of your infrastructure assets from eucalyptus & AWS cloud like Compute, Volume, Snapshot, Security Group, ip address, keypairs and Images(view only as of now).
  * Control the consumption by setting Workflows.
  * View Usage reports at user, department, Account and project level.
  * Manager, User and Super Admin roles supported as of now.

# Introduction #

MyCP isolates data and cloud configurations based on an ‘Account’.

![http://dl.dropbox.com/u/48692478/image09.png](http://dl.dropbox.com/u/48692478/image09.png)


---


Every Account in MyCP has many Departments on one end and many cloud configurations on the other end. Each Cloud can have many products setup. Each Department can have many Projects and each Project will have Users.
The User's cloud consumptions are maintained through the Asset entity.
Users with ROLE\_USER access have limited access to menu options as well as dashboard data.

![http://dl.dropbox.com/u/48692478/image11.png](http://dl.dropbox.com/u/48692478/image11.png)


Users with ROLE\_MANAGER access have full access menu options as well as dashboard data.

![http://dl.dropbox.com/u/48692478/image13.png](http://dl.dropbox.com/u/48692478/image13.png)

# Configure #

## Step 1 – Sign up ##

Signup at the mycloudprotal.in

![http://dl.dropbox.com/u/48692478/image14.png](http://dl.dropbox.com/u/48692478/image14.png)


The signup process will create an account by the organization name; the process will also setup a dummy Department and Project for this account. The signed up user is created with Manager role, this role will have all rights across this account including creating further users.


## Step 2 – Configure Cloud. ##

After the Sign in , you need to setup your Eucalyptus or AWS cloud configuration .
Go to Configuration --> Cloud.

![http://dl.dropbox.com/u/48692478/image15.png](http://dl.dropbox.com/u/48692478/image15.png)


Click on edit button.

![http://dl.dropbox.com/u/48692478/image01.png](http://dl.dropbox.com/u/48692478/image01.png)

Edit your eucalyptus or AWS details and save the form

![http://dl.dropbox.com/u/48692478/image00.png](http://dl.dropbox.com/u/48692478/image00.png)

You can add as many clouds as you want through "new cloud". You can also "Sync" pre existing data from eucalyptus or AWS cloud into my cloud portal. This functionality is one-way, meaning; the assets in the cloud will be imported into my cloud portal only. You can do this whenever you feel that my cloud portal has some data inconsistencies between its database and the underlying cloud.

![http://dl.dropbox.com/u/48692478/image03.png](http://dl.dropbox.com/u/48692478/image03.png)

## Step 3 – Configure Product Types. ##

Go to Configuration --> Product Type.
> In the hosted model you cannot change or add new types. In the on-premise version , you can add new product type and configure whether this type needs to be billed or if a approval is needed when a user requests for the services based on these product types.

![http://dl.dropbox.com/u/48692478/image02.png](http://dl.dropbox.com/u/48692478/image02.png)

## Step 4 – Configure Product and its price. ##

Go to Configuration --> Product.
In the hosted model, you cannot add new products but can edit existing ones to change its names or its price.

![http://dl.dropbox.com/u/48692478/image04.png](http://dl.dropbox.com/u/48692478/image04.png)

## Step 5 – Set up Account, Department, Projects. ##

Go to Setup --> Account. Edit the details to add in more details and change the currency. The currency is reflected in all dashboards and reports.

![http://dl.dropbox.com/u/48692478/image05.png](http://dl.dropbox.com/u/48692478/image05.png)

Go to Setup --> Department to change the automatically created details of the dummy department.
You can create as many Departments as you want. Later when you create the projects , you shall assign these departments to different projects.
Go to Setup --> Project to do the same.
You can create as many Projects as you want. Later when you are creating users, you shall assign these projects to different users.

## Step 6 – Set up Users for your account. ##
Go to Setup --> User

![http://dl.dropbox.com/u/48692478/image06.png](http://dl.dropbox.com/u/48692478/image06.png)

Click on "New User".
Fill out the needed details.
Select the ROLE\_USER. (You can also create a user with role MANAGER )
Make sure to click on Active checkbox to enable logins to this user.
Assign a project to this user.

![http://dl.dropbox.com/u/48692478/image08.png](http://dl.dropbox.com/u/48692478/image08.png)

## Step 7 – Start consuming resources. ##

Every user, whether with MANAGER\_ROLE or with USER\_ROLE access rights can consume resources by going into Resources --> any menu.
Every resource request must be done on a product which is preconfigured. This has 2 advantages, one to track usage and costs. The other is for the system to support multiple clouds.

![http://dl.dropbox.com/u/48692478/image10.png](http://dl.dropbox.com/u/48692478/image10.png)

In the hosted model, every request will go through a workflow.  In the on-premise model, you can configure this behavior for every product.
Whenever user requests a resource, the account manager has to approve the request.

Click on "Request Compute"

![http://dl.dropbox.com/u/48692478/image18.png](http://dl.dropbox.com/u/48692478/image18.png)

Fill the details in the popup. Hit Save.

![http://dl.dropbox.com/u/48692478/image19.png](http://dl.dropbox.com/u/48692478/image19.png)

The workflow pending status will be shown,

![http://dl.dropbox.com/u/48692478/image20.png](http://dl.dropbox.com/u/48692478/image20.png)

As a manager , go into Control --> workflows

![http://dl.dropbox.com/u/48692478/image21.png](http://dl.dropbox.com/u/48692478/image21.png)

Approve or reject the request .

![http://dl.dropbox.com/u/48692478/image16.png](http://dl.dropbox.com/u/48692478/image16.png)

Once approved, check in resources --> compute. The server status will be shown as starting.

![http://dl.dropbox.com/u/48692478/image17.png](http://dl.dropbox.com/u/48692478/image17.png)

The process is the same for all assets/products in the resources menu except for the images.

## Step 8 – Check Usage reports. ##

As a manager, Go into  Usage reports --> any menu

![http://dl.dropbox.com/u/48692478/image12.png](http://dl.dropbox.com/u/48692478/image12.png)

For example User reports show consumption per user.

![http://dl.dropbox.com/u/48692478/image07.png](http://dl.dropbox.com/u/48692478/image07.png)


# Download and Install #

MyCloudPortal is a simple java web application using Spring & Hibernate. Although this should work in any J2EE container , It has been tested on Tomcat 6.x and Mysql 5.x . This document explains the installation process of mycloudportal.

**Apache Tomcat 6.x**

Download and install Apache Tomcat 6.x from http://tomcat.apache.org/download-60.cgi and http://tomcat.apache.org/tomcat-6.0-doc/setup.html

**MySql 5.x installation**

Download and install MySql 5.x from http://www.mysql.com/downloads/mysql/ and http://dev.mysql.com/doc/refman/5.5/en/installing.html

Make sure your mysql installation is case insensitive by adding the below line into my.cnf file

lower\_case\_table\_names= 1

## Download Mycloudportal ##

Download the web app from http://code.google.com/p/mycloudportal/downloads/list

## Setup Mycloudportal ##
> Unzip the web application into ROOT folder in your tomcat ‘webapps’ folder.

http://dl.dropbox.com/u/48692478/exploded_root.PNG


## Setup mycp database ##
> Create a schema named ‘mycp’ in your mysql installation.
> Execute the mycp.ddl.sql first and then the mycp.dml.sql .
> Test and make sure the schema is created properly in mysql.

http://dl.dropbox.com/u/48692478/table_list.PNG

## Edit configuration files ##

Edit ROOT\WEB-INF\classes\jbpm.hibernate.cfg.xml and change the DB username,password and schema details.
Edit ROOT\WEB-INF\classes\META-INF\spring\database.properties , and do the same.

If you want email integration , you can setup your gmail account in ROOT\WEB-INF\classes\META-INF\spring\email.properties.

# Deploy & start mycloudportal #

Start tomcat and check out catalina.out for any error or success logs.
After successful deploy, go to your browser and enter http://localhost:<your port>
The initial setup of mycloudportal comes with a default  super admin account. This gives you complete access across any account in the locally installed mycloudportal.
> superadmin@mycloudportal.in /password
You can use it to login and go through the application or you can signup in the login page to create a new account.