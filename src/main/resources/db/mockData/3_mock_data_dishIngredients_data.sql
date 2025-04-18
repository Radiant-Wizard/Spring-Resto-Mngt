INSERT INTO dish_ingredients (dish_id, ingredient_id, quantity) VALUES
(1, 1, 1),
(1, 2, 100),
(1, 3, 0.15),
(1, 4, 1);

INSERT INTO dish_ingredients (dish_id, ingredient_id, quantity) VALUES
((SELECT dish_id FROM dishes WHERE dish_name = 'Pizza Margherita'), (SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Tomato'), 2),
((SELECT dish_id FROM dishes WHERE dish_name = 'Pizza Margherita'), (SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Cheese'), 150),
((SELECT dish_id FROM dishes WHERE dish_name = 'Pizza Margherita'), (SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Pasta'), 200),

((SELECT dish_id FROM dishes WHERE dish_name = 'Spaghetti Carbonara'), (SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Pasta'), 250),
((SELECT dish_id FROM dishes WHERE dish_name = 'Spaghetti Carbonara'), (SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Cream'), 0.1),
((SELECT dish_id FROM dishes WHERE dish_name = 'Spaghetti Carbonara'), (SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Ham'), 100),

((SELECT dish_id FROM dishes WHERE dish_name = 'Caesar Salad'), (SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Tomato'), 1),
((SELECT dish_id FROM dishes WHERE dish_name = 'Caesar Salad'), (SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Cheese'), 50),
((SELECT dish_id FROM dishes WHERE dish_name = 'Caesar Salad'), (SELECT ingredient_id FROM ingredients WHERE ingredient_name = 'Ham'), 50)
ON CONFLICT DO NOTHING;