CREATE TABLE customer(
customer_id integer primary key autoincrement,
first_name varchar(30) not null,
last_name varchar(30) not null,
age integer not null,
sex varchar(15) not null
);

CREATE TABLE bought(
shopping_trip_id integer not null,
product_id integer not null,
amount integer not null,
primary key(shopping_trip_id, product_id)
);

CREATE TABLE coordinate(
shopping_trip_id int,
timestamp varchar(255),
x real not null,
y real not null,
primary key(shopping_trip_id, timestamp)
);

CREATE TABLE action(
shopping_trip_id integer,
timestamp varchar(255),
actionType integer not null,
product_id integer not null,
primary key(shopping_trip_id, timestamp),
foreign key (product_id) references product(product_id)
);

CREATE TABLE on_shelf(
shop_id integer,
product_id integer,
amount_on_shelfs integer not null,
amount_in_storage integer not null,
primary key(shop_id, product_id)
);

CREATE TABLE shop(
shop_id integer primary key,
zip integer not null,
address varchar(30) not null,
foreign key(shop_id) references product(shop_id)
);

CREATE TABLE shopping_trip(
shopping_trip_id integer  primary key autoincrement,
customer_id integer not null,
shop_id integer not null,
foreign key(customer_id) references customer(customer_id),
foreign key(shop_id) references shop(shop_id),
foreign key(shopping_trip_id) references action(shopping_trip_id),
foreign key(shopping_trip_id) references coordinate(shopping_trip_id),
foreign key(shopping_trip_id) references bought(shopping_trip_id)
);

CREATE TABLE product(
product_id integer primary key autoincrement,
name varchar(255) not null,
price double not null
);