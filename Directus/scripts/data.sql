-- Crear rol de usuario
INSERT INTO directus_roles (id, name, icon, description, parent) VALUES
('11111111-1111-1111-1111-111111111111', 'Usuario', 'supervised_user_circle', 'Usuario con permisos para alquilar y vender productos', NULL);

-- Insertar usuarios de prueba
INSERT INTO directus_users (id, first_name, email, password, role, status)
VALUES
  (gen_random_uuid(), 'Carlos Cuesta', 'carlos@tienda.com', '$argon2i$v=19$m=16,t=2,p=1$bkxNSDBNaXNKMjJCVkJGVQ$49Tr4sR4s3BSK3ZRdTR06w', (SELECT id FROM directus_roles WHERE name = 'Usuario'), 'active'),
  (gen_random_uuid(), 'Sandra Lozano', 'sandra@tienda.com', '$argon2i$v=19$m=16,t=2,p=1$bkxNSDBNaXNKMjJCVkJGVQ$49Tr4sR4s3BSK3ZRdTR06w', (SELECT id FROM directus_roles WHERE name = 'Usuario'), 'active');

-- Insertar tipos de notificaciones
INSERT INTO notification_type (name) VALUES 
('Nuevo mensaje'), 
('Confirmación de reserva'), 
('Cancelación de reserva'), 
('Recordatorio'), 
('Promoción');

-- Insertar estados de ítems
INSERT INTO item_status (name) VALUES 
('Disponible'), 
('Reservado'), 
('Mantenimiento'), 
('No disponible'), 
('Pendiente');

-- Insertar categorías
INSERT INTO category (name) VALUES 
('Electrónica'), 
('Herramientas'), 
('Muebles'), 
('Juguetes'), 
('Deportes');

-- Crear perfiles de usuario
INSERT INTO user_profile (address, profile_photo, user_id)
VALUES 
('Calle 123', 'foto1.jpg', (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta')),
('Carrera 45', 'foto2.jpg', (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano'));

-- Insertar ítems (Productos)
INSERT INTO item (title, description, price_per_day, location, availability, photo, profile_id, category_id, status_id) VALUES 
('Taladro Bosch', 'Taladro eléctrico profesional', 20.00, 'Bogotá', true, 'taladro.jpg',
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta')),
  (SELECT id FROM category WHERE name = 'HerramienStas'),
  (SELECT id FROM item_status WHERE name = 'Disponible')),
('Bicicleta MTB', 'Bicicleta de montaña en buen estado', 30.00, 'Medellín', true, 'bici.jpg',
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano')),
  (SELECT id FROM category WHERE name = 'Deportes'),
  (SELECT id FROM item_status WHERE name = 'Disponible'));

-- Insertar notificaciones
INSERT INTO notifications (content, send_date, profile_id, notification_type_id) VALUES 
('Tienes un nuevo mensaje.', NOW(),
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta')),
  (SELECT id FROM notification_type WHERE name = 'Nuevo mensaje')),
('Tu reserva fue confirmada.', NOW(),
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano')),
  (SELECT id FROM notification_type WHERE name = 'Confirmación de reserva'));

-- Insertar reviews
INSERT INTO review (rating, comment, publication_date, profile_id, item_id) VALUES 
(5, 'Excelente calidad', NOW(),
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta')),
  (SELECT id FROM item WHERE title = 'Taladro Bosch')),
(4, 'Buen estado', NOW(),
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano')),
  (SELECT id FROM item WHERE title = 'Bicicleta MTB'));

-- Insertar estados de reserva
INSERT INTO reservation_status (name) VALUES 
('Pendiente'), 
('Confirmada'), 
('Cancelada'), 
('Completada'), 
('En curso');

-- Insertar reservas
INSERT INTO reservation (start_date, completion_date, total_price, profile_id, item_id, status_id) VALUES 
('2025-04-10', '2025-04-12', 40.00,
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta')),
  (SELECT id FROM item WHERE title = 'Taladro Bosch'),
  (SELECT id FROM reservation_status WHERE name = 'Confirmada')),
('2025-04-11', '2025-04-13', 60.00,
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano')),
  (SELECT id FROM item WHERE title = 'Bicicleta MTB'),
  (SELECT id FROM reservation_status WHERE name = 'Completada'));

-- Insertar métodos de pago
INSERT INTO payment_method (name, description) VALUES 
('Tarjeta de crédito', 'Visa, MasterCard, etc.'),
('Nequi', 'Pago desde celular'),
('Daviplata', 'Pago móvil bancario'),
('PSE', 'Débito directo desde cuenta bancaria'),
('Efectivo', 'Pago físico en oficina');

-- Insertar estados de pago
INSERT INTO payment_status (name) VALUES 
('Pendiente'), 
('Pagado'), 
('Fallido'), 
('Reembolsado'), 
('En proceso');

-- Insertar pagos
INSERT INTO payment (amount, payment_date, reservation_id, status_id, method_id) VALUES 
(40.00, '2025-04-10',
  (SELECT id FROM reservation WHERE total_price = 40.00),
  (SELECT id FROM payment_status WHERE name = 'Pagado'),
  (SELECT id FROM payment_method WHERE name = 'Tarjeta de crédito')),
(60.00, '2025-04-11',
  (SELECT id FROM reservation WHERE total_price = 60.00),
  (SELECT id FROM payment_status WHERE name = 'Pagado'),
  (SELECT id FROM payment_method WHERE name = 'Nequi'));
