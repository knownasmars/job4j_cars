CREATE TABLE auto_users(
    id serial PRIMARY KEY,
    login varchar unique not null,
    password varchar not null
);