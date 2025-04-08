create type stock_movement_type as ENUM('IN', 'OUT');

create table stock_movement(
    stock_movement_id BIGSERIAL primary key,
    ingredient_id BIGINT references ingredients(ingredient_id),
    movement_type stock_movement_type,
    quantity numeric(10, 2),
    unit measurement_unit,
    movement_date timestamp
);
