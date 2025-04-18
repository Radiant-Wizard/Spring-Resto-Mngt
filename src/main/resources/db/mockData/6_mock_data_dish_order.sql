INSERT INTO order_dish VALUES
(1, 1, 1, 3);

INSERT INTO order_dish (dish_id, order_id, ordered_dish_quantity) VALUES
((SELECT dish_id FROM dishes WHERE dish_name = 'Pizza Margherita'), (SELECT order_id FROM orders WHERE order_reference = 'ORD001'), 2),
((SELECT dish_id FROM dishes WHERE dish_name = 'Spaghetti Carbonara'), (SELECT order_id FROM orders WHERE order_reference = 'ORD001'), 1),
((SELECT dish_id FROM dishes WHERE dish_name = 'Caesar Salad'), (SELECT order_id FROM orders WHERE order_reference = 'ORD002'), 3),
((SELECT dish_id FROM dishes WHERE dish_name = 'Spaghetti Carbonara'), (SELECT order_id FROM orders WHERE order_reference = 'ORD003'), 1);