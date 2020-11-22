create table game_discounts
(
	id int not null
		primary key,
	start timestamp null,
	end timestamp null,
	rate int null,
	game_id int not null,
	constraint fk_discounts_games1
		foreign key (game_id) references games (id)
);

create index fk_discounts_games1_idx
	on game_discounts (game_id);

