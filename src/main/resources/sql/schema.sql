CREATE TABLE users (
	id serial primary key,
	avatar_id int,
	name varchar,
	surname varchar,
	phone varchar,
	email varchar,
	password varchar,
    common_memory bigint
);

CREATE TABLE logins (
	id serial primary key,
	user_id int,
	date varchar,
	time varchar,
	ip varchar
);

CREATE TABLE images (
	id serial primary key,
	user_id int,
	original_name varchar,
	unique_name varchar,
	size bigint,
	mime varchar
);
