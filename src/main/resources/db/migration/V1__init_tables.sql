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
    product_id bigint not null,
    category_id bigint not null,
  	FOREIGN KEY (product_id) REFERENCES products(id),
  	FOREIGN KEY (category_id) REFERENCES categories(id),
  	primary key (product_id, category_id)
);