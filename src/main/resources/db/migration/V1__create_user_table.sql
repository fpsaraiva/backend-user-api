create schema if not exists ecommerce;

create table ecommerce.user (
    id bigserial primary key,
    name varchar(255) not null,
    email varchar(100) unique not null,
    password varchar(100) not null,
    created_at timestamp default current_timestamp
);