CREATE TABLE products
(
  id serial not null,
  product_name varchar(50) not null,
  description varchar(255),
  price decimal not null,
  currency varchar(3) not null,
  sku varchar(50) not null,
  PRIMARY KEY(id)
)