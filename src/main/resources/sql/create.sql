DROP TABLE book IF EXISTS;
DROP TABLE author IF EXISTS;
DROP TABLE country IF EXISTS;
DROP SEQUENCE IF EXISTS book_id_seq;
DROP SEQUENCE IF EXISTS author_id_seq;
DROP SEQUENCE IF EXISTS country_id_seq;


CREATE SEQUENCE country_id_seq 
	START WITH 1 
	INCREMENT BY 1
;
CREATE SEQUENCE author_id_seq 
	START WITH 1 
	INCREMENT BY 1
;
CREATE SEQUENCE book_id_seq 
	START WITH 1 
	INCREMENT BY 1
;


CREATE TABLE country (
	country_id INTEGER 
		GENERATED BY DEFAULT AS SEQUENCE country_id_seq
		NOT NULL,
	country_name VARCHAR(250) NULL
);
CREATE TABLE author (
	author_id INTEGER 
		GENERATED BY DEFAULT AS SEQUENCE author_id_seq
		NOT NULL,
	author_country_id INTEGER NULL,
	author_name VARCHAR(250) NULL
);
CREATE TABLE book (
	book_id INTEGER 
		GENERATED BY DEFAULT AS SEQUENCE book_id_seq
		NOT NULL,
	book_author_id INTEGER  NULL,
	book_title VARCHAR(250) NULL, 
	book_genre VARCHAR(250) NULL
);


ALTER TABLE country ADD CONSTRAINT country_pk
	PRIMARY KEY (country_id)
;
ALTER TABLE author ADD CONSTRAINT author_pk
	PRIMARY KEY (author_id)
;
ALTER TABLE book ADD CONSTRAINT book_pk
	PRIMARY KEY (book_id)
;


ALTER TABLE author ADD CONSTRAINT author_to_country_fk
	FOREIGN KEY (author_country_id) REFERENCES country (country_id)
	ON UPDATE CASCADE 
	ON DELETE RESTRICT
;
ALTER TABLE book ADD CONSTRAINT book_to_author_fk
	FOREIGN KEY (book_author_id) REFERENCES author (author_id)
	ON UPDATE CASCADE 
	ON DELETE RESTRICT
;