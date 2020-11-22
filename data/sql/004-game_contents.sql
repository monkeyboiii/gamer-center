create table game_contents
(
	id int not null,
	name varchar(45) null,
	type varchar(45) null,
	path varchar(255) null,
	game_id int not null,
	primary key (id, game_id),
	constraint fk_images_games1
		foreign key (game_id) references games (id)
);

create index fk_images_games1_idx
	on game_contents (game_id);

