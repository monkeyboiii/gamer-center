create table users
(
	id int not null
		primary key,
	name varchar(45) null,
	email varchar(45) not null,
	password varchar(255) null,
	balance decimal(9,2) default 0.00 null,
	role varchar(10) null comment 'a/p/d
admin/player/develop',
	avatar varchar(255) null,
	bio varchar(2048) null,
	is_online tinyint null,
	is_locked tinyint default 0 not null,
	created_at timestamp default CURRENT_TIMESTAMP null,
	constraint email_UNIQUE
		unique (email),
	constraint name_UNIQUE
		unique (name)
);

