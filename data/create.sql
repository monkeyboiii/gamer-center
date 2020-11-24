create schema gamercenter collate utf8_general_ci;

create table games
(
	id int not null
		primary key,
	name varchar(45) null,
	price int null,
	is_announced tinyint null,
	is_downloadable tinyint null,
	score double null,
	description varchar(2048) null
);

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

create table hibernate_sequence
(
	next_val bigint null
);

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

create index fk_personalized_lists_users1_idx
	on game_lists (users_id);

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

create table post_comments
(
	id int not null
		primary key,
	parent_id int null,
	content varchar(2048) null,
	created_at timestamp null,
	visible tinyint default 1 not null,
	user_id int not null,
	post_id int not null,
	constraint fk_post_comments_posts1
		foreign key (post_id) references posts (id),
	constraint fk_post_comments_users1
		foreign key (user_id) references users (id)
);

create index fk_post_comments_posts1_idx
	on post_comments (post_id);

create index fk_post_comments_users1_idx
	on post_comments (user_id);

create table post_contents
(
	id int not null
		primary key,
	name varchar(45) null,
	type varchar(45) null,
	path varchar(255) null,
	post_id int not null,
	constraint fk_post_contents_posts1
		foreign key (post_id) references posts (id)
);

create index fk_post_contents_posts1_idx
	on post_contents (post_id);

create index fk_posts_games1_idx
	on posts (game_id);

create index fk_posts_users1_idx
	on posts (author_id);

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

