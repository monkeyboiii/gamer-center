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

