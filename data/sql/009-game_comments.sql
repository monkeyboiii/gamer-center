create table game_comments
(
	id int not null
		primary key,
	parent_id int null,
	content varchar(2048) null,
	created_at timestamp null,
	user_id int not null,
	game_id int not null,
	constraint fk_comments_games1
		foreign key (game_id) references games (id),
	constraint fk_comments_users1
		foreign key (user_id) references users (id)
);

create index fk_comments_games1_idx
	on game_comments (game_id);

create index fk_comments_users1_idx
	on game_comments (user_id);

