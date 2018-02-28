CREATE TABLE customer(
customer_id integer primary key autoincrement,
first_name varchar(30) not null,
last_name varchar(30) not null,
age int not null,
sex varchar(15) not null
);

CREATE TABLE bought(
shopping_trip_id int not null,
product_id int not null,
amount int not null,
primary key(shopping_trip_id, product_id)
);

CREATE TABLE zip(
zipcode int primary key,
city varchar(30) not null
);

CREATE TABLE coordinate(
shopping_trip_id int,
timestamp varchar(255),
x real not null,
y real not null,
primary key(shopping_trip_id, timestamp)
);

CREATE TABLE action(
shopping_trip_id int,
timestamp varchar(255),
actionType int not null,
product_id int not null,
primary key(shopping_trip_id, timestamp),
foreign key (product_id) references product(product_id)
);

CREATE TABLE on_shelf(
shop_id int,
product_id int,
amount int not null,
primary key(shop_id, product_id)
);

CREATE TABLE shop(
shop_id int primary key,
address varchar(30) not null,
zip int not null,
foreign key(zip) references zip(zipcode),
foreign key(shop_id) references on_shelf(shop_id),
foreign key(shop_id) references storage(shop_id)
);

CREATE TABLE in_storage(
storage_id int,
product_id int,
amount int not null,
primary key(storage_id, product_id)
);

CREATE TABLE storage(
storage_id int,
shop_id int,
primary key(storage_id, shop_id),
foreign key(storage_id) references in_storage(storage_id)
);

CREATE TABLE shopping_trip(
shopping_trip_id integer primary key autoincrement,
customer_id int not null,
shop_id int not null,
foreign key(customer_id) references customer(customer_id),
foreign key(shop_id) references shop(shop_id),
foreign key(shopping_trip_id) references action(shopping_trip_id),
foreign key(shopping_trip_id) references coordinate(shopping_trip_id),
foreign key(shopping_trip_id) references bought(shopping_trip_id)
);

CREATE TABLE product(
product_id integer primary key autoincrement,
name varchar(255) not null,
price double not null,
foreign key(product_id) references on_shelf(product_id),
foreign key(product_id) references bought(product_id)
);
