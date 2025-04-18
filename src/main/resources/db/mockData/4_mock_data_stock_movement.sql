INSERT INTO stock_movement (stock_movement_id, ingredient_id, movement_type, quantity, unit, movement_date)
VALUES
    (1, 4, 'IN', 100.00, 'U', '2025-02-01 08:00:00'),
    (2, 1, 'IN', 50.00, 'U', '2025-02-01 08:00:00'),
    (3, 2, 'IN', 10000.00, 'G', '2025-02-01 08:00:00'),
    (4, 3, 'IN', 20.00, 'L', '2025-02-01 08:00:00'),
    (5, 4, 'OUT', 10.00, 'U', '2025-02-02 10:00:00'),
    (6, 4, 'OUT', 10.00, 'U', '2025-02-02 15:00:00'),
    (7, 1, 'OUT', 20.00, 'U', '2025-02-05 16:00:00');

INSERT INTO stock_movement (ingredient_id, movement_type, quantity, unit, movement_date) VALUES
((SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Tomato'), 'IN', 50, 'U', '2025-04-01 08:00:00'),
((SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Cheese'), 'IN', 1000, 'G', '2025-04-01 08:00:00'),
((SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Pasta'), 'IN', 2000, 'G', '2025-04-01 08:00:00'),
((SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Cream'), 'IN', 5, 'L', '2025-04-01 08:00:00'),
((SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Ham'), 'IN', 500, 'G', '2025-04-01 08:00:00');