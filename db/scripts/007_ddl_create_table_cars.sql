create table cars(
    id serial primary key,
    name varchar not null,
    engine_id int not null references engines(id)
);