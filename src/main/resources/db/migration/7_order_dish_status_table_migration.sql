CREATE TABLE if not exists order_dish_status(
    order_status_id BIGSERIAL PRIMARY KEY,
    order_dish_id BIGINT references order_dish(order_dish_id)  ON DELETE CASCADE,
    order_dish_status statusType,
    order_dish_creation_date TIMESTAMP without time zone
);


