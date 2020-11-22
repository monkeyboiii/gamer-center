create table posts
(
	id int not null
		primary key,
	title varchar(45) null,
	description varchar(2048) null,
	text mediumtext null comment '1. store as file in file system
2. html',
	created_at timestamp null,
	visible tinyint null,
	author_id int not null,
	game_id int null,
	constraint fk_posts_games1
		foreign key (game_id) references games (id),
	constraint fk_posts_users1
		foreign key (author_id) references users (id)
);

create index fk_posts_games1_idx
	on posts (game_id);

create index fk_posts_users1_idx
	on posts (author_id);

