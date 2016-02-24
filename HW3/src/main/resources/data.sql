CREATE DATABASE IF NOT EXISTS spring_cdp;

use spring_cdp;

DROP TABLE IF EXISTS event_counter;
DROP TABLE IF EXISTS user_counter;
DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS seances;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS auditoriums;

CREATE TABLE users(
	id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(25) NOT NULL,
	email VARCHAR(25) NOT NULL,
	birthday Date NOT NULL
);

CREATE TABLE events(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(25) NOT NULL,
	ticketPrice INT(8) NOT NULL,
	rating VARCHAR(10) NOT NULL,
	duration TIMESTAMP NOT NULL
);

CREATE TABLE auditoriums(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(25) NOT NULL,
	number_of_seats INT(8) NOT NULL,
	vip_seats VARCHAR(255) NOT NULL
);

CREATE TABLE seances(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	eventId BIGINT NOT NULL,
	auditoriumId BIGINT NOT NULL,
	dateTime TIMESTAMP NOT NULL,
	FOREIGN KEY (eventId) REFERENCES events(id),
	FOREIGN KEY (auditoriumId) REFERENCES auditoriums(id)
);

CREATE TABLE tickets(
    id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	userId BIGINT NOT NULL,
	eventId BIGINT NOT NULL,
	date Date NOT NULL,
	seats VARCHAR(255) NOT NULL,
	FOREIGN KEY (userId) REFERENCES users(id),
	FOREIGN KEY (eventId) REFERENCES events(id)
);

CREATE TABLE event_counter(
    eventId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	getEventByName BIGINT NOT NULL,
	getTicketPrice BIGINT NOT NULL,
	bookedTicket BIGINT NOT NULL,
	FOREIGN KEY (eventId) REFERENCES events(id)
);

CREATE TABLE user_counter(
    userId BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	birthday BIGINT NOT NULL,
	FOREIGN KEY (userId) REFERENCES users(id)
);

INSERT INTO `users` (`id`, `name`, `email`, `birthday`) VALUES
(1, 'andrii22', 'test@gmail.com', '2014-01-14');

INSERT INTO `events` (`id`, `name`, `ticketPrice`, `rating`, `duration`) VALUES
(1, 'movie_1', 55, 'high', '2016-09-03 09:05:00');

INSERT INTO `auditoriums` (`id`, `name`, `number_of_seats`, `vip_seats`) VALUES
(1, 'Title_4186', 50, '2,5,6,45');

INSERT INTO `auditoriums` (`id`, `name`, `number_of_seats`, `vip_seats`) VALUES
(2, 'Title_4187', 50, '2,5,6,45');
