create table game_list_has_games
(
	game_list_id int not null,
	game_id int not null,
	primary key (game_list_id, game_id),
	constraint fk_personalized_lists_has_games_games1
		foreign key (game_id) references games (id),
	constraint fk_personalized_lists_has_games_personalized_lists1
		foreign key (game_list_id) references game_lists (id)
);

create index fk_personalized_lists_has_games_games1_idx
	on game_list_has_games (game_id);

create index fk_personalized_lists_has_games_personalized_lists1_idx
	on game_list_has_games (game_list_id);

