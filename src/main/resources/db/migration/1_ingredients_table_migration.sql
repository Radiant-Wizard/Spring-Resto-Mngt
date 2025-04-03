create type measurement_unit as ENUM('G', 'L', 'U');

create table ingredients(
    ingredient_id BIGSERIAL primary key,
    ingredient_name varchar(50) not null,
    last_modification timestamp without time zone,
    unit_price NUMERIC(10,2),
    unit measurement_unit
);