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

