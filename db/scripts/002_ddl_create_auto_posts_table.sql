CREATE TABLE auto_posts(
    id SERIAL PRIMARY KEY,
	description text,
	created timestamp,
	auto_user_id int REFERENCES auto_users
);