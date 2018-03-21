<h1><img src="https://i.imgur.com/NaEWdZ3.png" width="100" />&nbsp; &nbsp; E.C. Shop :apple: :bacon: :hamburger: :stuffed_flatbread:</h1>

<img src="https://gitlab.stud.iie.ntnu.no/tdt4140-2018/64/badges/master/build.svg">
<img src="https://gitlab.stud.iie.ntnu.no/tdt4140-2018/64/badges/master/coverage.svg">

E.C. Shop is a system for tracking customers in a store, similarly to systems such as Amazon Shop and Go. The system tracks customer inventories as they move through the store, and automatically charges their credit cards upon leaving the store with goods. The system also provides customers with data such as reccomended items, and shop owners with statistics about their shop such as items in stock, and visualizations of customer movements throughout the shop.

## Feature overview

### Features for service providers (shop owners)

### Features for data providers (customers)

## Installation

To install the application for local development on your machine, start by cloning the repo by issuing the following command in the terminal.

``` bash
git clone https://gitlab.stud.iie.ntnu.no/tdt4140-2018/64.git
```

This project is configured for development with Eclipse, and so we reccomend you download Eclipse and import the project into your Eclipse installation. To import the project, right click the Project Explorer and select Import. Select Maven, and use the directory named `64` which you just cloned as the root directory. Clicking Finish should complete the importing of the project. We reccomend installing Eclipse using the guide for the [TDT4100 course at NTNU](https://www.ntnu.no/wiki/display/tdt4100/Installasjon+av+Eclipse), but using the setup file found [here](https://raw.githubusercontent.com/hallvard/jexercise/master/no.hal.learning/TDT4180.setup) instead. This installation includes Maven, and a plethora of other plugins as well.

Once the repository has been downloaded, `cd` into the directory. Then run the following command in order to download all dependencies using Maven.

```bash
mvn clean install
```

You should see the test suite running. If all tests pass, the project is fully functional. Once this is done, you can start the web server. The web server runs in the background and provides a simple REST API for interfacing with the application.

```bash
cd tdt4140-gr1864/web.server
mvn jetty:start
```

Lastly, you should start the shop owner user interface, which is where the majority of the functionality is actually visualized. To start the user interface, open Eclipse, right click the file `app.ui/src/main/java/tdt4140.gr1864.app.ui`, and select `Run As > Java Application`. This should start the shop owner user interface.

## System architecture

## License
