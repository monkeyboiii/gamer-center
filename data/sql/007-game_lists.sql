create table game_lists
(
	id int not null
		primary key,
	name varchar(45) null,
	users_id int not null,
	description varchar(45) null,
	created_at timestamp null,
	updated_at timestamp null,
	constraint fk_personalized_lists_users1
		foreign key (users_id) references users (id)
);

create index fk_personalized_lists_users1_idx
	on game_lists (users_id);

