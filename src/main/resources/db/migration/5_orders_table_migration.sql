create type statusType as ENUM('CREATED', 'CONFIRMED', 'IN_PROGRESS', 'FINISHED', 'SERVED');

CREATE TABLE if not exists orders(
    order_id BIGSERIAL PRIMARY KEY,
    order_reference varchar(15) UNIQUE
);