# Documentation for Planner

## Table of Contents
- [Overview](#overview)
- [Technologies used](#technologies-used)
- [Requirements](#requirements)
  * [Hardware requirements](#hardware-requirements)
  * [Software requirements](#software-requirements)
- [Installation instructions](#installation-instructions)
  * [Configuring MySQL server database and table](#configuring-mysql-server-database-and-table)
  * [Running from sources under Eclipse IDE](#running-from-sources-under-eclipse-ide)
- [Usage instructions](#usage-instructions)
  * [Adding a new plan](#adding-a-new-plan)
  * [Editing existing plan](#editing-existing-plan)
  * [Removing existing plan](#removing-existing-plan)
  * [Additional usage information](#additional-usage-information)
- [General project and class structure](#general-project-and-class-structure)

Overview
========
Application goal is to provide the user with the ability to schedule 15 to 120 minute long appointments in a provided room. In addition the application suggests another appointment date and time if selected room already has an appointment scheduled  within selected time range. All currently scheduled appointments are listed in application interface and also from there can be edited or removed as necessary.
Application uses web interface to accomplish above goals and MySQL back-end to store scheduled appointments. Implementation is based on Spring and Hibernate frameworks.

Technologies used
=================
Application has been created using following technologies:
* Spring Framework 5.0.2
* Hibernate 5.2.12
* MySQL 5.7
* Tomcat 8 or 9

Requirements
============

## Hardware requirements
To run application with minimal traffic (a few simultaneous users) a basic system that is capable of efficiently running JVM and Tomcat will be sufficient. In addition properly configured TCP/IP stack is necessary, since this is a web-based application.
No specific hardware is necessary to run the application.

## Software requirements
Main software requirement to run Planner is to have JDK 1.8 (Java Development Kit) installed and properly set environment variables in the operating system. Please refer to JDK installation instructions on how to accomplish this. Application has been tested and can also be run on OpenJDK 8 if necessary.
There is no requirement in regards to operating system used. The application was tested on both Microsoft Windows and Linux operating systems. Any system capable of running JVM and for which JDK is available can be used to run the application. This documentation however focuses only on Microsoft Windows and Linux operating systems.
Tomcat 9 (Tomcat 8 was tested and is supported as well) is used as a server/container for deployment of the application. Other servers/containers can be used, however this document only covers Tomcat.

Installation instructions
=========================
This guide assumes that JDK is properly installed and configured on target system. Installation instructions for JDK can be found here: [JDK Installation for Microsoft Windows](https://docs.oracle.com/javase/7/docs/webnotes/install/windows/jdk-installation-windows.html) or [JDK Installation for Linux Platforms](https://docs.oracle.com/javase/8/docs/technotes/guides/install/linux_jdk.html).
Tomcat 8/9 must be properly configured prior to running the application. Please refer to Tomcat documentation found at: [Tomcat 9 documentation](https://tomcat.apache.org/tomcat-9.0-doc/config/index.html) to properly configure and run Tomcat.
Additionally MySQL server needs to be available to provide database for the application. It can be run on local machine or remotely. Please refer to installation instructions on how to install MySQL on target machine: [Installing MySQL on Microsoft Windows](https://dev.mysql.com/doc/refman/5.7/en/windows-installation.html) and [Installing MySQL on Linux](https://dev.mysql.com/doc/refman/5.7/en/linux-installation.html).
Please note that running the application on Linux operating system may require additional configuration based on type of distribution used. Given there are many distributions  available it is impossible to list and describe here all steps necessary to run this application on every version of Linux.

## Configuring MySQL server database and table
After MySQL installation and configuration if complete, database and table need to be created for use by the application. To do so the user must login into “MySQL Command Line Client” and execute SQL statements from attached “planner.sql” file. File is included in Tomcat package as well as in Eclipse project (under Planner\src\main\resources\sql folder). Instructions on how this can be accomplished can be found here: [Executing SQL Statements from a Text File](https://dev.mysql.com/doc/refman/5.7/en/mysql-batch-commands.html).
Successful completion of this step correctly configures tables and databases required for Planner.

## Running from sources under Eclipse IDE
Application can also be run directly from Eclipse IDE using provided Eclipse project source files. There is an additional requirement to have „Eclipse IDE for Java EE Developers” installed to run this from sources.
This guide assumes user is familiar with Eclipse and has the project already imported into the IDE. Another assumption is that the user already added Tomcat 8/9 as a server to Eclipse.
Project requires changes to „applicationContext.xml” file to properly access database as described in previous point. In Eclipse project this file can be found under:

```
[project_folder]\src\webapp\WEB-INF\ applicationContext.xml
```

Inside the file three properties need to be updated according to database configuration:
1. „url” - defines hostname of the MySQL server. By default it is assumed that database is run on the same machine as the application (hence usage of localhost). If this is not the case, then appropriate IP address or hostname must be provided. Please note it must be followed by port on which MySQL server is hosted (3306 is the default) and database name after slash. This must be set to „planner” (without the quotes). Address prefix (jdbc:mysql://) should not be changed.
Examples:

```
jdbc:mysql://localhost:3306/planner – local server
jdbc:mysql://123.123.123.123:3306/planner – remote server by IP
jdbc:mysql://somemysqlserver:3306/planner – remote server by hostname
```

2. „username” - as stated, user name for the MySQL database. Can be set to „root” if database allows it or other created user that has read/write access to „planner” database.
3. „password” - password to the database
After following above steps and running Tomcat, application should be available under address: http://localhost:8080/Planner. This is assuming default Tomcat configuration.

Usage instructions
==================

## Adding a new plan
To successfully schedule an appointment the user must provide four pieces of information (all fields are required):
* Username – user who is scheduling an appointment (alphanumerical, max 20 characters),
* From and to date – date and time of the appointment in dd-MM-yyyy HH:mm format (where dd is day in two digit format, MM is month in two digit format, yyyy is year in four digit format, HH is hour in two digit format, mm is minutes in two digit format),
* Room number – room number which will be reserved for the appointment (numerical only, min 0 max 60000 ).

After that clicking on „Add plan” button will add an appointment into the database and it will be displayed in below table under „All current plans”. In case of conflicting appointments, appropriate information will be displayed with suggestion of another date. Also in case of entering inappropriate data, like for example scheduling a meeting shorter than 15 minutes or longer than 120 minutes appropriate information will be displayed.

## Editing existing plan
Each plan that was added can be edited by clicking „Edit” link next to appropriate record. Plan data is then loaded into the form where it can be edited and after completing changes – saved. Same rules apply as for adding a new plan.

## Removing existing plan
Similarly to edition, user can remove existing plan by clicking „Delete” next to appropriate record. Please note there is no confirmation dialog and record is deleted immediately after clicking the link. This operation cannot be undone.

## Additional usage information
There is no validation implemented at this time, because of that care must be taken when entering data into application form. Omitting fields, exceeding stated character limits and using different date formats will result in exceptions thrown by the JVM.

General project and class structure
===================================
Project was created using typical structure for Maven-based web application. There are four main packages with Java classes:

* com.xaffax.dao – contains Hibernate DAO abstract definition and implementation classes,
* com.xaffax.entities – contains Hibernate entity bean which defines database structure in Java class,
* com.xaffax.service – contains Spring service classes that work with Planner objects by using Hibernate DAO classes,
* com.xaffax – contains controller class which is responsible for client requests, redirections and uses service classes to do database operations.

Additionally project contains following files:
* web.xml – deployment descriptor that contains definition for Spring DispatcherServlet as well as applicationContext location,
applicationContext.xml – covers Spring Bean configuration – dataSource is for connection pooling and contains all information needed to open connection to database, sessionFactory is used for Hibernate session factory, transactionManager is for Spring ORM support of Hibernate session transaction management,
* planner-servlet.xml – defines removal of .jsp suffixes from URLs,
* pom.xml – defines all Maven dependencies,
* log4j.xml – log4j configuration,
* planner.jsp – view definition of the webpage, contains entire web interface,
* planner.sql – SQL script that allows creation of appropriate database and table for the application.