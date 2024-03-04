-- Вставка пользователей user 111 admin 222
INSERT INTO users (u_id, username, password, email)
VALUES (1, 'user', '$2y$10$/E7lBz5rK/Z/EXdTRGHjm.3lRxxkGsWyBjT.0oYAc.nNAKEoVt/9K', 'USER_NIK@example.com');

INSERT INTO users (u_id, username, password, email)
VALUES (2, 'admin', '$2y$10$8S5mqtrCGb9HYGNmZEg/2OqOsZ7OYJY4ejEcUuuhH6d5.oR8UVNMS', 'ADMIN_MARIA@example.com');

-- Вставка ролей
INSERT INTO roles (id, role_name) VALUES (1, 'ROLE_USER');
INSERT INTO roles (id, role_name) VALUES (2, 'ROLE_ADMIN');

-- Вставка прав
INSERT INTO authorities (id, authority) VALUES (1, 'READ_PRIVILEGE');
INSERT INTO authorities (id, authority) VALUES (2, 'WRITE_PRIVILEGE');

-- Связывание пользователей и ролей
INSERT INTO user_role (user_id, role_id) VALUES (1, 1); -- user is a ROLE_USER
INSERT INTO user_role (user_id, role_id) VALUES (2, 2); -- admin is a ROLE_ADMIN

-- Связывание ролей и прав
INSERT INTO role_authority (role_id, authority_id) VALUES (1, 1); -- ROLE_USER has READ_PRIVILEGE
INSERT INTO role_authority (role_id, authority_id) VALUES (2, 1); -- ROLE_ADMIN has READ_PRIVILEGE
INSERT INTO role_authority (role_id, authority_id) VALUES (2, 2); -- ROLE_ADMIN has WRITE_PRIVILEGE