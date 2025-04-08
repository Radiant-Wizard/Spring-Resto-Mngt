create type measurement_unit as ENUM('G', 'L', 'U');

create table ingredients(
    ingredient_id BIGSERIAL primary key,
    ingredient_name varchar(50) not null,
    unit measurement_unit
);