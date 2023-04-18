create table history_owners(
       id serial PRIMARY KEY,
       car_id int REFERENCES cars(id),
       owner_id int REFERENCES owners(id),
       startAt TIMESTAMP,
       endAt TIMESTAMP
);