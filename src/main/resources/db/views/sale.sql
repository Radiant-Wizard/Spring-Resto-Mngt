create view sale as SELECT
    d.dish_name,
    d.dish_price AS unit_price,
    SUM(od.ordered_dish_quantity) AS total_quantity_ordered,
    COUNT(od.order_dish_id) AS total_orders,
    SUM(d.dish_price * od.ordered_dish_quantity) AS total_amount
FROM
    order_dish od
JOIN
    order_dish_status ods ON ods.order_dish_id = od.order_dish_id
JOIN
    dishes d ON d.dish_id = od.dish_id
WHERE
    ods.order_dish_status = 'FINISHED'
GROUP BY
    d.dish_name, d.dish_price
ORDER BY
    total_quantity_ordered DESC;