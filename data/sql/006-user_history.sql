create table user_history
(
	id int not null
		primary key,
	users_id int not null,
	balance_change int null,
	created_at timestamp null,
	rest_balance int null,
	constraint fk_purchase_history_users1
		foreign key (users_id) references users (id)
);

create index fk_purchase_history_users1_idx
	on user_history (users_id);

