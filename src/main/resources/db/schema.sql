DROP TABLE IF EXISTS role_authority;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
                       u_id INT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS roles (
                       id INT PRIMARY KEY,
                       role_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities (
                             id INT PRIMARY KEY,
                             authority VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS user_role (
                           user_id INTEGER NOT NULL,
                           role_id INTEGER NOT NULL,
                           PRIMARY KEY (user_id, role_id),
                           FOREIGN KEY (user_id) REFERENCES users (u_id),
                           FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE role_authority (
                                role_id INTEGER NOT NULL,
                                authority_id INTEGER NOT NULL,
                                PRIMARY KEY (role_id, authority_id),
                                FOREIGN KEY (role_id) REFERENCES roles (id),
                                FOREIGN KEY (authority_id) REFERENCES authorities (id)
);