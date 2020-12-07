create table game
(
    id             bigint auto_increment
        primary key,
    announce_date  varchar(255) null,
    branch         varchar(255) null,
    description    varchar(255) null,
    developer_id   bigint       not null,
    discount_end   varchar(255) null,
    discount_rate  double       not null,
    discount_start varchar(255) null,
    front_image    varchar(255) null,
    name           varchar(255) null,
    price          double       not null,
    release_date   varchar(255) null,
    score          double       not null,
    tag            varchar(255) null
);

create table game_comment
(
    game_comment_id bigint       not null
        primary key,
    content         varchar(255) null,
    game_id         bigint       not null,
    grade           double       not null,
    user_id         bigint       not null
);

create table game_content
(
    id      bigint auto_increment
        primary key,
    game_id bigint       not null,
    name    varchar(255) null,
    path    varchar(255) null,
    type    varchar(255) null
);

create table game_game_contents
(
    game_id          bigint not null,
    game_contents_id bigint not null,
    constraint UK_67au2hxsmdlsxdsphjbgqhos8
        unique (game_contents_id),
    constraint FK631soouy7g08y126kabiijt0r
        foreign key (game_contents_id) references game_content (id),
    constraint FKm2e5jggj74x9hbnsa70dftojy
        foreign key (game_id) references game (id)
);

create table hibernate_sequence
(
    next_val bigint null
);

create table users
(
    id          bigint                                  not null
        primary key,
    name        varchar(45)                             not null,
    email       varchar(45)                             not null,
    password    varchar(255)                            not null,
    balance     decimal(9, 2) default 0.00              null,
    role        varchar(10)   default 'p'               null comment 'a/p/d/t combinations, denoting admin/player/develop/developer',
    avatar      varchar(255)                            null,
    bio         varchar(2048) default ''                null,
    online      bit           default b'0'              null,
    locked      bit           default b'0'              not null,
    created_at  timestamp     default CURRENT_TIMESTAMP null,
    last_online timestamp                               null,
    constraint email_UNIQUE
        unique (email),
    constraint name_UNIQUE
        unique (name)
);

create table users_friends
(
    from_user_id bigint                        not null,
    to_user_id   bigint                        not null,
    status       varchar(45) default 'pending' null,
    primary key (from_user_id, to_user_id),
    constraint fk_users_friends_id1_in_users
        foreign key (from_user_id) references users (id),
    constraint fk_users_friends_id2_in_users
        foreign key (to_user_id) references users (id)
);

create index idx_users_friends_users1
    on users_friends (from_user_id);

create index idx_users_friends_users2
    on users_friends (to_user_id);

create table users_games_token
(
    id           bigint auto_increment,
    user_id      bigint       not null,
    developer_id bigint       not null,
    game_id      bigint       not null,
    token        varchar(255) not null,
    constraint users_games_token_id_uindex
        unique (id),
    constraint users_games_token_game_id_fk
        foreign key (game_id) references game (id),
    constraint users_games_token_users_id_fk
        foreign key (user_id) references users (id),
    constraint users_games_token_users_id_fk_2
        foreign key (developer_id) references users (id)
)
    comment 'third-party-authorization';

alter table users_games_tokens
    add primary key (id);

create table users_messages
(
    id         bigint auto_increment,
    source     bigint      default 0                 null,
    user_id    bigint                                not null,
    type       varchar(45) default 'default'         null,
    message    varchar(255)                          not null,
    unread     bit         default b'1'              not null,
    created_at timestamp   default CURRENT_TIMESTAMP null,
    constraint users_messages_id_uindex
        unique (id),
    constraint users_messages_users_id_fk
        foreign key (user_id) references users (id)
)
    comment 'unread_messages';

alter table users_messages
    add primary key (id);

create table users_purchases
(
    id             bigint auto_increment,
    user_id        bigint                              not null,
    balance_change decimal(9, 2)                       null,
    created_at     timestamp default CURRENT_TIMESTAMP null,
    rest_balance   decimal(9, 2)                       not null,
    constraint users_purchases_id_uindex
        unique (id)
);

create index idx_users_purchases
    on users_purchases (user_id);

alter table users_purchases
    add primary key (id);

create table users_games
(
    user_id     bigint            not null,
    game_id     bigint            not null,
    purchase_id bigint            not null,
    valid       tinyint default 1 not null,
    user_tag    varchar(45)       null,
    primary key (user_id, game_id, valid),
    constraint fk_users_games_games
        foreign key (game_id) references game (id),
    constraint fk_users_games_user
        foreign key (user_id) references users (id),
    constraint users_games_users_purchases_id_fk
        foreign key (purchase_id) references users_purchases (id)
);

create index fk_users_games_games1_idx
    on users_games (game_id);

create index fk_users_games_user_history1_idx
    on users_games (purchase_id);

create index fk_users_games_users1_idx
    on users_games (user_id);

