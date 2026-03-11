-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
create table address
(
    id bigserial not null primary key,
    street varchar(255)
);


-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)
create sequence client_SEQ start with 1 increment by 1;

create table client
(
    id bigint not null primary key,
    name varchar(50),

    address_id bigint,
    constraint fk_address
        foreign key (address_id)
            references address(id)
);


-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
create table phone
(
    id bigserial not null primary key,
    number varchar(50),

    client_id bigint,
    constraint fk_client
        foreign key (client_id)
            references client(id)
);
