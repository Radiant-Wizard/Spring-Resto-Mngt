INSERT INTO ingredients (ingredient_id, ingredient_name, unit) VALUES
(1, 'Bread', 'U'),
(2, 'Sausage','G'),
(3, 'Oil','L'),
(4, 'Egg','U');

INSERT INTO ingredients (ingredient_name, unit) VALUES
('Tomato', 'U'),
('Cheese', 'G'),
('Pasta', 'G'),
('Cream', 'L'),
('Ham', 'G')
ON CONFLICT DO NOTHING;