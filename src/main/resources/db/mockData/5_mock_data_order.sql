insert into orders values
(1, 'CMD001'),
(2, 'CMD002'),
(3, 'CMD003');

INSERT INTO orders (order_reference) VALUES
('ORD001'),
('ORD002'),
('ORD003')
ON CONFLICT DO NOTHING;