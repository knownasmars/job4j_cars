CREATE TABLE auto_users(
    id SERIAL PRIMARY KEY,
    login varchar unique not null,
    password varchar not null
);