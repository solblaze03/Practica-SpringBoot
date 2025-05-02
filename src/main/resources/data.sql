INSERT INTO category(name) VALUES ('EuroGames')
INSERT INTO category(name) VALUES ('Ameritrash')
INSERT INTO category(name) VALUES ('Familiar')


INSERT INTO author(name, nationality) VALUES ('Alan R. Moon', 'US')
INSERT INTO author(name, nationality) VALUES ('Vital Lacerda', 'PT')
INSERT INTO author(name, nationality) VALUES ('Simone Luciani', 'IT')
INSERT INTO author(name, nationality) VALUES ('Perepau Llistosella', 'ES')
INSERT INTO author(name, nationality) VALUES ('Michael Kiesling', 'DE')
INSERT INTO author(name, nationality) VALUES ('Phil Walker-Harding', 'US')

INSERT INTO game(title, age, category_id, author_id) VALUES ('Final fantasy VII', '14', 1, 2)
INSERT INTO game(title, age, category_id, author_id) VALUES ('Aventureros al tren', '8', 3, 1)
INSERT INTO game(title, age, category_id, author_id) VALUES ('1920: Wall Street', '12', 1, 4)
INSERT INTO game(title, age, category_id, author_id) VALUES ('Barrage', '14', 1, 3)
INSERT INTO game(title, age, category_id, author_id) VALUES ('Los viajes de Marco Polo', '12', 1, 3)
INSERT INTO game(title, age, category_id, author_id) VALUES ('Azul', '8', 3, 5)


INSERT INTO customer(name) VALUES ('Michael Kiesling')
INSERT INTO customer(name) VALUES ('Phil Walker-Harding')
INSERT INTO customer(name) VALUES ('Alan R. Moon')
INSERT INTO customer(name) VALUES ('George Harrison')

INSERT INTO loan(game_id, customer_id, fechainicio, fechadevolucion) VALUES (1,1,'2025-09-16','2025-09-19')
INSERT INTO loan(game_id, customer_id, fechainicio, fechadevolucion) VALUES (2,3,'2025-08-15','2025-09-15')

-- INSERT INTO loan(game_id, customer_id, fechainicio, fechadevolucion) VALUES (3,2,'2025-08-15','2025-09-15')
-- INSERT INTO loan(game_id, customer_id, fechainicio, fechadevolucion) VALUES (2,2,'2025-08-15','2025-09-15')
-- INSERT INTO loan(game_id, customer_id, fechainicio, fechadevolucion) VALUES (5,1,'2025-08-15','2025-09-15')
-- INSERT INTO loan(game_id, customer_id, fechainicio, fechadevolucion) VALUES (2,4,'2025-08-15','2025-09-15')
-- INSERT INTO loan(game_id, customer_id, fechainicio, fechadevolucion) VALUES (1,1,'2025-08-15','2025-09-15')
-- INSERT INTO loan(game_id, customer_id, fechainicio, fechadevolucion) VALUES (3,3,'2025-08-15','2025-09-15')
-- INSERT INTO loan(game_id, customer_id, fechainicio, fechadevolucion) VALUES (1,1,'2025-08-15','2025-09-15')
-- INSERT INTO loan(game_id, customer_id, fechainicio, fechadevolucion) VALUES (1,4,'2025-08-15','2025-09-15')
-- INSERT INTO loan(game_id, customer_id, fechainicio, fechadevolucion) VALUES (1,1,'2025-08-15','2025-09-15')
-- INSERT INTO loan(game_id, customer_id, fechainicio, fechadevolucion) VALUES (1,1,'2025-08-15','2025-09-15')
-- INSERT INTO loan(game_id, customer_id, fechainicio, fechadevolucion) VALUES (1,1,'2025-08-15','2025-09-15')
