CREATE TABLE product
(
    id          bigserial primary key,
    title       varchar(300),
    description varchar(500),
    price       numeric(38, 2),
    image       varchar(100),
    created     timestamp
);

CREATE TABLE "order"
(
    id         bigserial primary key,
    payment_id bigint,
    created    timestamp
);

CREATE TABLE order_part
(
    id         bigserial primary key,
    price       numeric(38, 2),
    quantity int4,
    order_id bigint references "order"(id),
    product_id bigint references product(id)
);

CREATE TABLE payment
(    id         bigserial primary key,
    payment_date timestamp,
    order_id bigint references "order"(id),
    sum numeric(38,2)
);

ALTER TABLE "order" ADD foreign key (payment_id) references payment(id);
