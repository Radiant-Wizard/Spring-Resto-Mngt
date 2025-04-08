create view ingredient_with_prices as SELECT
       i.ingredient_id,
       i.ingredient_name,
       p.last_modification,
       p.unit_price,
       i.unit
FROM ingredients i
JOIN prices p ON i.ingredient_id = p.ingredient_id;
