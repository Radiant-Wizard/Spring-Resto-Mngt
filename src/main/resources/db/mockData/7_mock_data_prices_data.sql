INSERT INTO prices (ingredient_id, last_modification, unit_price) VALUES
(1, NOW(), 1000),
(2, NOW(), 20),
(3, NOW(), 10000),
(4, NOW(), 1000);

INSERT INTO prices (ingredient_id, last_modification, unit_price) VALUES
((SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Tomato'), '2025-03-25 00:00:00', 500.00),
((SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Cheese'), '2025-03-25 00:00:00', 200.00),
((SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Pasta'), '2025-03-25 00:00:00', 100.00),
((SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Cream'), '2025-03-25 00:00:00', 1500.00),
((SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Ham'), '2025-03-25 00:00:00', 250.00);