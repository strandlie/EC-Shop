<h1><img src="https://i.imgur.com/sTrXVki.png =100x20" width="100" />&nbsp; &nbsp; E.C. Shop :apple: :bacon: :hamburger: :stuffed_flatbread:</h1>

<img src="https://gitlab.stud.iie.ntnu.no/tdt4140-2018/64/badges/master/build.svg">
<img src="https://gitlab.stud.iie.ntnu.no/tdt4140-2018/64/badges/master/coverage.svg">

E.C. Shop is a system for tracking customers in a store, similarly to systems such as Amazon Shop and Go. The system tracks customer inventories as they move through the store, and automatically charges their credit cards upon leaving the store with goods. The system also provides customers with data such as reccomended items, and shop owners with statistics about their shop such as items in stock, and visualizations of customer movements throughout the shop.

## Feature overview

### Features for service providers (shop owners)

### Features for data providers (customers)

## Installation
Start by cloning the repo: 
``` bash
git clone https://gitlab.stud.iie.ntnu.no/tdt4140-2018/64.git
```

Then run a clean install, maven will download all dependencies
```bash
mvn clean install
```

If all tests pass, the project is fully functional

Start up the server:
```bash
cd web.server
mvn jetty:start
```

Then run the userUI
```bash
Go into Eclipse and run Ownerapp.java which is located inside the app.ui folder
```






## System architecture

## License
