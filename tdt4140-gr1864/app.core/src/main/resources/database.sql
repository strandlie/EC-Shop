CREATE TABLE customer(
customer_id integer primary key autoincrement,
first_name varchar(30) not null,
last_name varchar(30) not null,
address	varchar(31),
zip integer,
gender varchar(31),
age int,
num_persons_in_household int,
anonymous boolean,
deleted boolean,
foreign key(customer_id) references shopping_trip(customer_id)
);

CREATE TABLE bought(
shopping_trip_id integer not null,
product_id integer not null,
amount integer not null,
primary key(shopping_trip_id, product_id)
);

CREATE TABLE coordinate(
shopping_trip_id integer,
timestamp varchar(255),
x real not null,
y real not null,
primary key(shopping_trip_id, timestamp),
foreign key (shopping_trip_id) references shopping_trip(shopping_trip_id)
);

CREATE TABLE action(
shopping_trip_id integer,
timestamp varchar(255),
action_type integer not null,
product_id integer not null,
primary key(shopping_trip_id, timestamp),
foreign key (product_id) references product(product_id),
foreign key (shopping_trip_id) references shopping_trip(shopping_trip_id)
);

CREATE TABLE on_shelf(
shop_id integer not null,
product_id integer no null,
amount_on_shelfs integer not null,
amount_in_storage integer not null,
primary key(shop_id, product_id),
foreign key (shop_id) references shop(shop_id),
foreign key (product_id) references product(product_id)
);

CREATE TABLE shop(
shop_id integer primary key,
zip integer not null,
address varchar(30) not null,
foreign key(shop_id) references product(shop_id)
);

CREATE TABLE shopping_trip(
shopping_trip_id integer primary key autoincrement,
customer_id integer not null,
shop_id integer not null,
charged bit not null,
anonymous boolean not null,
foreign key(customer_id) references customer(customer_id),
foreign key(shop_id) references shop(shop_id),
foreign key(shopping_trip_id) references action(shopping_trip_id),
foreign key(shopping_trip_id) references coordinate(shopping_trip_id),
foreign key(shopping_trip_id) references bought(shopping_trip_id)
);

CREATE TABLE product (
product_id integer primary key autoincrement,
name varchar(255) not null,
price double not null
);
