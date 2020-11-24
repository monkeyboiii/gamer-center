create table users
(
    id         bigint                                  not null
        primary key,
    name       varchar(45)                             not null,
    email      varchar(45)                             not null,
    password   varchar(255)                            not null,
    balance    decimal(9, 2) default 0.00              null,
    role       varchar(10)   default 'p'               null comment 'a/p/d/t combinations, denoting admin/player/develop/developer',
    avatar     varchar(255)                            null,
    bio        varchar(2048) default ''                null,
    online     tinyint       default 0                 null,
    locked     tinyint       default 0                 not null,
    created_at timestamp     default CURRENT_TIMESTAMP null,
    constraint email_UNIQUE
        unique (email),
    constraint name_UNIQUE
        unique (name)
);

create table user_friendships
(
    from_user_id bigint                        not null,
    to_user_id   bigint                        not null,
    state        varchar(45) default 'pending' null,
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

create table user_history # purchase history
(
    id             bigint                              not null
        primary key,
    user_id        bigint                              not null,
    balance_change decimal(9, 2)                       null,
    created_at     timestamp default CURRENT_TIMESTAMP null,
    rest_balance   decimal(9, 2)                       not null,
    constraint fk_purchase_history_users1
        foreign key (user_id) references users (id)
);

create index fk_purchase_history_user_idx
    on user_history (user_id);

create table users_has_games
(
    user_id         bigint not null,
    game_id         bigint not null,
    user_history_id bigint not null,
    primary key (user_id, game_id),
    constraint fk_users_has_games_games
        foreign key (game_id) references games (id),
    constraint fk_users_has_games_user_history
        foreign key (user_history_id) references user_history (id),
    constraint fk_users_has_games_user
        foreign key (user_id) references users (id)
);

create index fk_users_has_games_games1_idx
    on users_has_games (game_id);

create index fk_users_has_games_user_history1_idx
    on users_has_games (user_history_id);

create index fk_users_has_games_users1_idx
    on users_has_games (user_id);

