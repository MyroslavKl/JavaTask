DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

CREATE TABLE books
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    author VARCHAR(100) NOT NULL,
    type VARCHAR(50) NOT NULL,
    releaseDate DATE NOT NULL,
    isbn BIGINT NOT NULL UNIQUE,
    language VARCHAR(50) NOT NULL,
    numberOfPages INTEGER NOT NULL,
    publishingHouse VARCHAR(100) NOT NULL,
    description TEXT
);