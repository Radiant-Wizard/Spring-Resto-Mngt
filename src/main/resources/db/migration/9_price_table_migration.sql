CREATE TABLE prices(
    price_id BIGSERIAL primary key,
    ingredient_id INT REFERENCES ingredients(ingredient_id) ON DELETE CASCADE,
    last_modification timestamp without time zone,
    unit_price NUMERIC(10,2)
);
