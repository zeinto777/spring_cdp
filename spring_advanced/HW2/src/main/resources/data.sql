CREATE DATABASE IF NOT EXISTS spring;

use spring;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS user_accounts;
DROP TABLE IF EXISTS users;

DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS persistent_logins;


CREATE TABLE users(
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(25) NOT NULL,
	email VARCHAR(25) NOT NULL,
    username VARCHAR(25) NOT NULL,
    password VARCHAR(65) NOT NULL,
    enabled TINYINT NOT NULL DEFAULT 1
);

CREATE TABLE persistent_logins (
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used timestamp not null,
    PRIMARY KEY (series)
);

CREATE TABLE user_roles(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    role varchar(45) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE events(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	title VARCHAR(25) NOT NULL,
	date DATE NOT NULL,
	ticket_price INTEGER NOT NULL
);

CREATE TABLE categories(
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(25) NOT NULL
);

CREATE TABLE tickets(
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	place INTEGER NOT NULL,
	event_id BIGINT NOT NULL,
	user_id BIGINT NOT NULL,
	category_id INTEGER NOT NULL,
	FOREIGN KEY (event_id) REFERENCES events(id),
	FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE user_accounts(
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	money INTEGER NOT NULL,
	user_id BIGINT NOT NULL,
	FOREIGN KEY (user_id) REFERENCES users(id)
);

INSERT INTO users VALUES(223991, 'Andrii Pinchuk', 'Andrii_Pinchuk1@epam.com','mkyong','$2a$10$Q0AbFGWymykBQ3WJs.QegOYIaSsTjLICFhics7bJcSk5Ay9B3oN6e', true);
INSERT INTO users VALUES(324558, 'Andrii Pinchuk', 'Andrii_Pinchuk@epam.com','alex','$2a$10$Q0AbFGWymykBQ3WJs.QegOYIaSsTjLICFhics7bJcSk5Ay9B3oN6e', true);
INSERT INTO users VALUES(324559, 'Andrii Pinchuk', 'Andrii_Pinchuk@epam.com','admin','$2a$10$Q0AbFGWymykBQ3WJs.QegOYIaSsTjLICFhics7bJcSk5Ay9B3oN6e', true);


INSERT INTO user_roles (user_id, role) VALUES (223991, 'REGISTERED_USER');
INSERT INTO user_roles (user_id, role) VALUES (324558, 'REGISTERED_USER');
INSERT INTO user_roles (user_id, role) VALUES (324559, 'BOOKING_MANAGER');


INSERT INTO events VALUES(344493,'Title_344493', '2015-11-17', 1000);
INSERT INTO events VALUES(930400,'Title_930400', '2015-11-17', 2000);
INSERT INTO events VALUES(306978,'Title_306978', '2015-11-17', 3000);

INSERT INTO user_accounts VALUES(15, 10000, 223991);
INSERT INTO user_accounts VALUES(16, 20000, 324558);
INSERT INTO user_accounts VALUES(17, 20000, 324559);


INSERT INTO categories VALUES(1, 'PREMIUM');
INSERT INTO categories VALUES(2, 'BAR');
INSERT INTO categories VALUES(3, 'STANDARD');

INSERT INTO tickets VALUES(593398, 1, 344493, 223991, 1);
INSERT INTO tickets VALUES(32506, 2, 930400, 324558, 2);
INSERT INTO tickets VALUES(32507, 2, 930400, 324559, 2);




