create table categories (
    id                  bigint          not null,
    auxiliary_category  boolean         not null,
    name                varchar(255),
    primary key (id)
);

create table products (
    id                  bigint          not null,
    closest_expiration  date,
    comment             varchar(255),
    name                varchar(255),
    quantity            integer         not null,
    quantity_threshold  integer         not null,
    primary key (id)
);

create table categories_products (
    product_id      bigint  foreign key references products(id)    not null,
    category_id     bigint  foreign key references categories(id)    not null,
    primary key (product_id, category_id)
);

-- alter table categories_products add foreign key(category_id) references  categories(id);
-- alter table categories_products add foreign key(product_id)  references  products(id);
