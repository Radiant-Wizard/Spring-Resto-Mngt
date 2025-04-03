create view dishes_with_ingredients as SELECT
       d.dish_name,
       d.dish_id,
       d.dish_price,
       i.ingredient_id,
       i.ingredient_name,
       i.last_modification,
       di.quantity,
       i.unit,
       i.unit_price,
       (i.unit_price * di.quantity) AS cost_per_ingredient
FROM dish_ingredients di
JOIN dishes d ON di.dish_id = d.dish_id
JOIN ingredients i ON di.ingredient_id = i.ingredient_id;
