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
('Calle 123, Bogotá', 'foto1.jpg', (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta')),
('Carrera 45, Medellín', 'foto2.jpg', (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano'));

-- Insertar ítems (Productos) - 3 productos por usuario
INSERT INTO item (title, description, price_per_day, location, availability, photo, profile_id, category_id, status_id) VALUES 
-- Productos de Carlos Cuesta
-- ¡OJO!: Cambiar el id del atributo photo de todos los items dependiendo del que está en Directus
('Taladro Bosch', 'Taladro eléctrico profesional con 3 años de garantía', 20.00, 'Bogotá', true, 'd8d5a289-a36a-4507-84c7-4ccaf494af6f',
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta')),
  (SELECT id FROM category WHERE name = 'Herramientas'),
  (SELECT id FROM item_status WHERE name = 'Disponible')),
('Silla Gamer', 'Silla ergonómica para gaming con soporte lumbar', 25.00, 'Bogotá', true, 'f4929f28-92e9-4e50-98e3-b1754a5976c3',
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta')),
  (SELECT id FROM category WHERE name = 'Muebles'),
  (SELECT id FROM item_status WHERE name = 'Disponible')),
('Impresora láser', 'Impresora HP LaserJet Pro MFP, multifuncional', 15.00, 'Bogotá', true, '08075b49-32cb-4ef1-8b2b-bf2992feae89',
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta')),
  (SELECT id FROM category WHERE name = 'Electrónica'),
  (SELECT id FROM item_status WHERE name = 'Disponible')),

-- Productos de Sandra Lozano
-- ¡OJO!: Cambiar el id del atributo photo de todos los items dependiendo del que está en Directus
('Bicicleta MTB', 'Bicicleta de montaña marca Trek, modelo 2023', 30.00, 'Medellín', true, '522db6d7-037f-4f42-9477-06aa73f10578',
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano')),
  (SELECT id FROM category WHERE name = 'Deportes'),
  (SELECT id FROM item_status WHERE name = 'Disponible')),
('Mouse inalámbrico', 'Mouse Logitech MX Master 3, como nuevo', 10.00, 'Medellín', true, 'b153d4b7-efab-4f29-a938-bb7ecbbbf2d6',
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano')),
  (SELECT id FROM category WHERE name = 'Electrónica'),
  (SELECT id FROM item_status WHERE name = 'Disponible')),
('Televisor LG 55"', 'Smart TV 4K con Android TV integrado', 25.00, 'Medellín', true, '8ab32c4b-8b24-494f-b20f-23c4e8968a73',
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano')),
  (SELECT id FROM category WHERE name = 'Electrónica'),
  (SELECT id FROM item_status WHERE name = 'Disponible'));

-- Insertar estados de reserva
INSERT INTO reservation_status (name) VALUES 
('Pendiente'), 
('Confirmada'), 
('Cancelada'), 
('Completada'), 
('En curso');

-- Insertar reservas (3 por usuario, reservando productos del otro usuario)
INSERT INTO reservation (start_date, completion_date, total_price, profile_id, item_id, status_id) VALUES 
-- Carlos reserva productos de Sandra
('2025-04-10', '2025-04-12', 60.00, -- Bicicleta (2 días)
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta')),
  (SELECT id FROM item WHERE title = 'Bicicleta MTB' AND profile_id = (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano'))),
  (SELECT id FROM reservation_status WHERE name = 'Completada')),
('2025-04-15', '2025-04-17', 20.00, -- Mouse (2 días)
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta')),
  (SELECT id FROM item WHERE title = 'Mouse inalámbrico' AND profile_id = (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano'))),
  (SELECT id FROM reservation_status WHERE name = 'Completada')),
('2025-04-20', '2025-04-22', 75.00, -- TV (3 días)
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta')),
  (SELECT id FROM item WHERE title = 'Televisor LG 55"' AND profile_id = (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano'))),
  (SELECT id FROM reservation_status WHERE name = 'Confirmada')),

-- Sandra reserva productos de Carlos
('2025-04-11', '2025-04-13', 40.00, -- Taladro (2 días)
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano')),
  (SELECT id FROM item WHERE title = 'Taladro Bosch' AND profile_id = (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta'))),
  (SELECT id FROM reservation_status WHERE name = 'Completada')),
('2025-04-16', '2025-04-18', 50.00, -- Silla (2 días)
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano')),
  (SELECT id FROM item WHERE title = 'Silla Gamer' AND profile_id = (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta'))),
  (SELECT id FROM reservation_status WHERE name = 'Completada')),
('2025-04-21', '2025-04-23', 45.00, -- Impresora (3 días)
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano')),
  (SELECT id FROM item WHERE title = 'Impresora láser' AND profile_id = (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta'))),
  (SELECT id FROM reservation_status WHERE name = 'Confirmada'));

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

-- Insertar pagos (uno por cada reserva)
INSERT INTO payment (amount, payment_date, reservation_id, status_id, method_id) VALUES 
-- Pagos de Carlos por productos de Sandra
(60.00, '2025-04-09',
  (SELECT id FROM reservation WHERE total_price = 60.00),
  (SELECT id FROM payment_status WHERE name = 'Pagado'),
  (SELECT id FROM payment_method WHERE name = 'Tarjeta de crédito')),
(20.00, '2025-04-14',
  (SELECT id FROM reservation WHERE total_price = 20.00),
  (SELECT id FROM payment_status WHERE name = 'Pagado'),
  (SELECT id FROM payment_method WHERE name = 'Nequi')),
(75.00, '2025-04-19',
  (SELECT id FROM reservation WHERE total_price = 75.00),
  (SELECT id FROM payment_status WHERE name = 'Pagado'),
  (SELECT id FROM payment_method WHERE name = 'Daviplata')),

-- Pagos de Sandra por productos de Carlos
(40.00, '2025-04-10',
  (SELECT id FROM reservation WHERE total_price = 40.00),
  (SELECT id FROM payment_status WHERE name = 'Pagado'),
  (SELECT id FROM payment_method WHERE name = 'Tarjeta de crédito')),
(50.00, '2025-04-15',
  (SELECT id FROM reservation WHERE total_price = 50.00),
  (SELECT id FROM payment_status WHERE name = 'Pagado'),
  (SELECT id FROM payment_method WHERE name = 'Nequi')),
(45.00, '2025-04-20',
  (SELECT id FROM reservation WHERE total_price = 45.00),
  (SELECT id FROM payment_status WHERE name = 'Pagado'),
  (SELECT id FROM payment_method WHERE name = 'PSE'));

-- Insertar reviews (una por cada reserva completada)
INSERT INTO review (rating, comment, publication_date, profile_id, item_id) VALUES 
-- Reviews de Carlos sobre productos de Sandra
(5, 'La bicicleta es excelente, muy buena calidad', '2025-04-13',
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta')),
  (SELECT id FROM item WHERE title = 'Bicicleta MTB')),
(4, 'Mouse muy preciso, solo que la batería duró menos de lo esperado', '2025-04-18',
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta')),
  (SELECT id FROM item WHERE title = 'Mouse inalámbrico')),

-- Reviews de Sandra sobre productos de Carlos
(5, 'Taladro potente, hizo el trabajo perfectamente', '2025-04-14',
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano')),
  (SELECT id FROM item WHERE title = 'Taladro Bosch')),
(4, 'Silla muy cómoda, aunque un poco pesada para transportar', '2025-04-19',
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano')),
  (SELECT id FROM item WHERE title = 'Silla Gamer'));

-- Insertar notificaciones relevantes
INSERT INTO notifications (content, send_date, profile_id, notification_type_id) VALUES 
('Tu reserva de la Bicicleta MTB ha sido confirmada', '2025-04-09',
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta')),
  (SELECT id FROM notification_type WHERE name = 'Confirmación de reserva')),
('Carlos Cuesta ha dejado una reseña sobre tu Bicicleta MTB', '2025-04-13',
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano')),
  (SELECT id FROM notification_type WHERE name = 'Nuevo mensaje')),
('Tu reserva del Taladro Bosch ha sido confirmada', '2025-04-10',
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Sandra Lozano')),
  (SELECT id FROM notification_type WHERE name = 'Confirmación de reserva')),
('Sandra Lozano ha dejado una reseña sobre tu Taladro Bosch', '2025-04-14',
  (SELECT id FROM user_profile WHERE user_id = (SELECT id FROM directus_users WHERE first_name = 'Carlos Cuesta')),
  (SELECT id FROM notification_type WHERE name = 'Nuevo mensaje'));