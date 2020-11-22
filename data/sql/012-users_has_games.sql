create table users_has_games
(
	user_id int not null,
	game_id int not null,
	user_history_id int not null,
	primary key (user_id, game_id),
	constraint fk_users_has_games_games1
		foreign key (game_id) references games (id),
	constraint fk_users_has_games_user_history1
		foreign key (user_history_id) references user_history (id),
	constraint fk_users_has_games_users1
		foreign key (user_id) references users (id)
);

create index fk_users_has_games_games1_idx
	on users_has_games (game_id);

create index fk_users_has_games_user_history1_idx
	on users_has_games (user_history_id);

create index fk_users_has_games_users1_idx
	on users_has_games (user_id);

