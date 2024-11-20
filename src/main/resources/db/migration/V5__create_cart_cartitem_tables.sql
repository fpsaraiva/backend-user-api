create table ecommerce.shopping_cart (
    id UUID primary key default gen_random_uuid(),
    user_id UUID not null,
    created_at timestamp default current_timestamp,
    foreign key (user_id) references ecommerce.user (id) on delete cascade
);

create table ecommerce.cart_item (
    id UUID primary key default gen_random_uuid(),
    product_id UUID not null,
	shopping_cart_id UUID not null,
    quantity int not null check (quantity > 0),
    created_at timestamp default current_timestamp,
    foreign key (product_id) references ecommerce.product(id) on delete cascade,
	foreign key (shopping_cart_id) references ecommerce.shopping_cart(id) on delete cascade
);