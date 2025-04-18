WITH dish_ids AS (
    SELECT od.order_dish_id, d.dish_name
    FROM order_dish od
    JOIN dishes d ON d.dish_id = od.dish_id
    JOIN orders o ON o.order_id = od.order_id
)

-- Pizza Margherita (ORD001)
INSERT INTO order_dish_status (order_dish_id, order_dish_status, order_dish_creation_date) VALUES
((SELECT order_dish_id FROM dish_ids WHERE dish_name = 'Pizza Margherita' LIMIT 1), 'CREATED', '2025-04-01 12:00:00'),
((SELECT order_dish_id FROM dish_ids WHERE dish_name = 'Pizza Margherita' LIMIT 1), 'IN_PROGRESS', '2025-04-01 12:10:00'),
((SELECT order_dish_id FROM dish_ids WHERE dish_name = 'Pizza Margherita' LIMIT 1), 'FINISHED', '2025-04-01 12:30:00'),
((SELECT order_dish_id FROM dish_ids WHERE dish_name = 'Pizza Margherita' LIMIT 1), 'SERVED', '2025-04-01 12:40:00');