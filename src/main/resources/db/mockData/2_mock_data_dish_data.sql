INSERT INTO dishes (dish_id, dish_name, dish_price) VALUES
(1, 'Hotdog', 8000);

INSERT INTO dishes (dish_name, dish_price) VALUES
('Pizza Margherita', 15000.00),
('Spaghetti Carbonara', 18000.00),
('Caesar Salad', 12000.00)
ON CONFLICT DO NOTHING;