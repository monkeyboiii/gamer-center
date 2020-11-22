create table user_friendships
(
	from_user_id int not null,
	to_user_id int not null,
	state varchar(45) null,
	primary key (from_user_id, to_user_id),
	constraint fk_users_has_users_users1
		foreign key (from_user_id) references users (id),
	constraint fk_users_has_users_users2
		foreign key (to_user_id) references users (id)
);

create index fk_users_has_users_users1_idx
	on user_friendships (from_user_id);

create index fk_users_has_users_users2_idx
	on user_friendships (to_user_id);

