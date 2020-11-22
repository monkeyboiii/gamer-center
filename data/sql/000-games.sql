create table games
(
	id int not null
		primary key,
	name varchar(45) null,
	price int null,
	is_announced tinyint null,
	is_downloadable tinyint null,
	score double null,
	description varchar(2048) null
);

