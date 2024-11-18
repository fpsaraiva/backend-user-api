create table ecommerce.product (
    id bigserial primary key,
    sku varchar(45) unique not null,
    name varchar(255) not null,
    price decimal(10, 2) not null check (price > 0),
    stock_quantity int not null check (stock_quantity >= 0),
    created_at timestamp default current_timestamp
);