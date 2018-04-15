# web.server module - REST API using Jetty and Servlet

A REST-API module for the *E.C. Store* application. Includes a functioning
REST-API (running on Jetty, using Java Servlets), and a persistance-layer ontop
of the database-base system in `app.core`.


## Dependencies
* `app.core`-modul
* Jetty 9.3.2
* Servlet 3.1

## End-points

### Authorization
To authorize your requests, query-param `"customer_id"` is required, with a
valid ID corresponding to the data that is manipulated.

### /api/v1/shoppingtrip/
#### Permitted actions
* `GET`: Retrieve shopping trips corresponding to a customer
* `POST`: Submit a shopping trip for a customer

#### Format
```json
{
	"customerID": [integer, required],
	"coordinates": [
		{ 
			"x": [double, required],
			"y": [double, required],
			"timestamp": [long, required]
		}
	],
	"actions": [
		{
			"actionType": [binary-integer, required],
			"productID": [integer, required],
			"timestamp": [long, required]
		}
	],
	"duration": [long, required]
}
```

### /api/v1/customer/
#### Permitted actions
* `GET`: Retrieve customer information
* `POST`: Submit a new customer 
* `PUT`: Update a custmer's data
* `DELETE`: Delete customer data

#### Format:
```json
{
	"firstName": [string, required],
	"lastName": [string, required],
	"address": [string],
	"zip": [integer],
	"gender": [string],
	"age": [integer],
	"numberOfPersonsInHousehold": [integer],
	"recommendedProductID": [integer, read-only],
	"anonymous": [boolean, required],
	"deleted": [boolean],
	"customerID": [integer, read-only]
}
```

### /api/v1/customer/recommendation/
#### Permitted actions
* `GET`: Retrieve purchase-recommendation for a customer

#### Format:
```json
{
	"name": [string, read-only],
	"price":[double, read-only],
	"id": [integer, read-only]
}
```

### /api/v1/customer/statistics/
#### Permitted actions
* `GET`: Retrieve purchase-statistics for a customer

#### Format:
```json
[
	{
		"amount": [integer, read-only],
		"product": {
			"name": [string, read-only],
			"price":[double, read-only],
			"id": [integer, read-only]
		}
	},
]
```

### /api/v1/customer/receipt/
#### Permitted actions:
* `GET`: Retrieve all receipts for a customer

#### Format:
```json
[
	{
		"date": [long, read-only],
		"charged": [boolean, read-only],
		"totalPrice": [double, read-only],
		"items": {
			[string, product-name, read-only]: {
				"total": [double, read-only],
				"amount": [integer, read-only]
			},
			[string, product-name, read-only]: {
				"total": [double, read-only],
				"amount": [integer, read-only]
			},
		}
	}
]
```
