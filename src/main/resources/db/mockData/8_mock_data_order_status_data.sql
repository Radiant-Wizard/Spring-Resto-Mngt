INSERT INTO order_status (order_id, order_status, order_creation_date) VALUES
((SELECT order_id FROM orders WHERE order_reference = 'ORD001'), 'CONFIRMED', '2025-04-01 11:50:00'),
((SELECT order_id FROM orders WHERE order_reference = 'ORD002'), 'CONFIRMED', '2025-04-02 10:50:00'),
((SELECT order_id FROM orders WHERE order_reference = 'ORD003'), 'CONFIRMED', '2025-04-03 17:50:00');