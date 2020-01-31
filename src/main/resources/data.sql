INSERT INTO users(id, username, password, email) VALUES
(1, 'u1', '123', 'mail@yahoo.com'),
(2, 'u2', '123', 'email@gmail.com'),
(3, 'u3', '123', 'adamczarodziej@xd.pl');

INSERT INTO roles(id, role) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_MODERATOR'),
(3, 'ROLE_USER');

INSERT INTO user_role(user_id, role_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 3),
(3, 2);


