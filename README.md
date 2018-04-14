<h1><img src="https://i.imgur.com/NaEWdZ3.png" width="100" />&nbsp; &nbsp; E.C.
Shop :apple: :bacon: :hamburger: :stuffed_flatbread:</h1>

<img src="https://gitlab.stud.iie.ntnu.no/tdt4140-2018/64/badges/master/build.svg">
<img src="https://gitlab.stud.iie.ntnu.no/tdt4140-2018/64/badges/master/coverage.svg">

This is a school project developed associated with the subject TDT4140 at NTNU.

E.C. Shop is a system for tracking customers in a store, similarly to systems
such as Amazon's Amazon Go. The system tracks customer inventories as they move
through the store, and automatically charges their credit cards upon leaving
the store with goods. The system also provides customers with data such as
recommended items, and shop owners with statistics about their shop such as
items in stock, and visualizations of customer movements throughout the shop.

- [Feature Overciew](#feature-overview)
	- [Service providers](#features-for-service-providers-shop-owners)
	- [Data providers](#features-for-data-providers-customers)
- [Installation](#installation)
- [System Architecture](#system-architecture)

## Feature overview
### Features for service providers / shop owners

Most of the features for service providers are shown in the owner application
user interface. 
* In the first tab, owners can see 
stats describing how often items are picked up, so that you can see which items
are popular. 
* In the second tab, owners can see how many items are remainding,
so that you know when restocking is needed. 
* The HeatMap and Plot tabs shows how
customers move in the store, and which areas of the store usually have the
largest amount of people. This can aid with the placement of items.

### Features for data providers / customers

The main feature for customers is automatically being charged for your picked
up goods when leaving store. In addition, our service provides multiple
endpoints providing recommendations for items to buy and descriptions of your
shopping habits. 

## Installation
To install the application for local development on your machine, start by
cloning the repo by issuing the following command in the terminal.

``` bash
git clone https://gitlab.stud.iie.ntnu.no/tdt4140-2018/64.git
```

This project is configured for development with Eclipse, and so we recommend
you download Eclipse and import the project into your Eclipse installation. 

To import the project, right click the Project Explorer and select Import.
Select Maven, and use the directory named `64` which you just cloned as the root
directory. Clicking Finish should complete the importing of the project. We
reccomend installing Eclipse using the guide for the [TDT4100 course at
NTNU](https://www.ntnu.no/wiki/display/tdt4100/Installasjon+av+Eclipse), but
using the setup file found
[here](https://raw.githubusercontent.com/hallvard/jexercise/master/no.hal.learning/TDT4180.setup)
instead. This installation includes Maven, and a plethora of other plugins as
well.

Once the repository has been downloaded, `cd` into the directory. Then run the
following command in order to download all dependencies using Maven.

```bash
mvn clean install
```

You should see the test suite running. If all tests pass, the project is fully
functional. Once this is done, you can start the web server. The web server
runs in the background and provides a simple REST API for interfacing with the
application.

```bash
cd tdt4140-gr1864/web.server
mvn jetty:start
```

Lastly, you should start the shop owner user interface, which is where the
majority of the functionality is actually visualized. To start the user
interface, open Eclipse, right click the file
`app.ui/src/main/java/tdt4140.gr1864.app.ui`, and select `Run As > Java
Application`. This should start the shop owner user interface.

## System architecture
The project is split into two main parts, `app.core` and `app.ui`. The
`app.core` package contains all the business logic such as computing receipts
and all the database handling/persistence, while `app.ui` contains the JavaFX
code for the owner user interface. Inside `app.core`, most of the business
logic such as computing receipts and modeling shopping trips resides in
`tdt4140.gr1864.app.core`. The package
`tdt4140.gr1864.app.core.databasecontrollers` contains code responsible for
interfacing with the database. The
`tdt4140.gr1864.app.core.datamocker` package is a standalone system
for generating fake yet reasonable shopping trips for local testing purposes,
and `tdt4140.gr1864.app.core.interfaces` contains interfaces used in different
parts of the system. 

The `web.server` module uses its own instance of `app.core` to pass and receive
data from customers. An external customer application could interact with the
relevant data through this REST API.

## List of tools we've used
* Jetty - a http server and servlet engine used as webserver
* Java servlet - a java program for web server
* GitLab - the core program for this project with it's version control using Git.
* Apache Maven - Dependency manager that describes how the programs are built and their dependencies and is used for automatically project building.
* PostMan - API development environment to send http requests
* Insomnia - Rest API client with cookie management, environment variables and code generation and authentication.
* Eclipse - An open source IDE that is primarly used to develop in this project
* JavaFX - A set of graphics and media packages.
* JDBC - Short for Java Database Connectivity and is a standardized interface for java applications that communicates with a database. 
