create table dev2.game
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

create table dev2.game_comment
(
    id         bigint auto_increment primary key,
    content    varchar(255)                        null,
    game_id    bigint                              not null,
    grade      double                              not null,
    user_id    bigint                              not null,
    visible    bit       default b'1'              null,
    created_at timestamp default CURRENT_TIMESTAMP null,
    constraint game_comment_id_uindex
        unique (id)
);


create table dev2.game_content
(
    id      bigint auto_increment
        primary key,
    game_id bigint       not null,
    name    varchar(255) null,
    path    varchar(255) null,
    type    varchar(255) null
);

create table dev2.game_dlc
(
    id      bigint auto_increment primary key,
    game_id bigint                     not null,
    name    varchar(255)               not null,
    price   decimal(9, 2) default 0.00 null,
    path    varchar(255)               null,
    visible bit           default b'1' null,
    constraint game_dlc_game_id_name_uindex
        unique (game_id, name),
    constraint game_dlc_id_uindex
        unique (id),
    constraint game_dlc_game_id_fk
        foreign key (game_id) references dev2.game (id)
);

create table dev2.hibernate_sequence
(
    next_val bigint null
);


#
#


create table dev2.users
(
    id          bigint auto_increment
        primary key,
    name        varchar(45)                             not null,
    email       varchar(45)                             not null,
    password    varchar(255)                            not null,
    balance     decimal(9, 2) default 0.00              null,
    role        varchar(10)   default 'p'               null comment 'a/p/d/t combinations, denoting admin/player/developer/tester',
    avatar      varchar(255)                            null,
    bio         varchar(2048)                           null,
    online      bit           default b'0'              null,
    locked      bit           default b'0'              not null,
    created_at  timestamp     default CURRENT_TIMESTAMP null,
    last_online timestamp                               null,
    constraint email_UNIQUE
        unique (email),
    constraint name_UNIQUE
        unique (name)
);

create table dev2.comment_report
(
    id         bigint auto_increment primary key,
    comment_id bigint           not null,
    user_id    bigint           not null,
    reason     varchar(255)     null,
    resolved   bit default b'0' null,
    constraint comment_report_id_uindex
        unique (id),
    constraint comment_report_pk
        unique (comment_id, user_id),
    constraint comment_report_game_comment_id_fk
        foreign key (comment_id) references dev2.game_comment (id),
    constraint comment_report_users_id_fk
        foreign key (user_id) references dev2.users (id)
)
    comment 'comments being reported';

create table dev2.users_collections
(
    id          bigint auto_increment
        primary key,
    user_id     bigint                              not null,
    name        varchar(255)                        not null,
    type        varchar(255)                        not null,
    path        varchar(255)                        not null,
    valid       bit       default b'1'              null,
    uploaded_at timestamp default CURRENT_TIMESTAMP null,
    constraint users_collections_path_uindex
        unique (path),
    constraint users_collections_users_id_fk
        foreign key (user_id) references dev2.users (id)
)
    comment 'user uploaded collections';

create table dev2.users_friends
(
    from_user_id bigint                        not null,
    to_user_id   bigint                        not null,
    status       varchar(45) default 'pending' null,
    primary key (from_user_id, to_user_id),
    constraint fk_users_friends_id1_in_users
        foreign key (from_user_id) references dev2.users (id),
    constraint fk_users_friends_id2_in_users
        foreign key (to_user_id) references dev2.users (id)
);

create index idx_users_friends_users1
    on dev2.users_friends (from_user_id);

create index idx_users_friends_users2
    on dev2.users_friends (to_user_id);

create table dev2.users_games_tokens
(
    id           bigint auto_increment primary key,
    user_id      bigint       not null,
    developer_id bigint       not null,
    game_id      bigint       not null,
    token        varchar(255) not null,
    constraint users_games_token_id_uindex
        unique (id),
    constraint users_games_token_game_id_fk
        foreign key (game_id) references dev2.game (id),
    constraint users_games_token_users_id_fk
        foreign key (user_id) references dev2.users (id),
    constraint users_games_token_users_id_fk_2
        foreign key (developer_id) references dev2.users (id)
)
    comment 'third-party authorization tokens';

create table dev2.users_messages
(
    id         bigint auto_increment primary key,
    source     bigint      default 0                 null comment '0 represents system messages',
    user_id    bigint                                not null,
    type       varchar(45) default 'default'         null,
    message    varchar(255)                          not null,
    unread     bit         default b'1'              not null,
    created_at timestamp   default CURRENT_TIMESTAMP null,
    constraint users_messages_id_uindex
        unique (id),
    constraint users_messages_users_id_fk
        foreign key (user_id) references dev2.users (id)
);

create table dev2.users_purchases
(
    id             bigint auto_increment primary key,
    user_id        bigint                              not null,
    balance_change decimal(9, 2)                       null,
    created_at     timestamp default CURRENT_TIMESTAMP null,
    rest_balance   decimal(9, 2)                       not null,
    constraint users_purchases_id_uindex
        unique (id)
);

create index idx_users_purchases
    on dev2.users_purchases (user_id);

create table dev2.users_games
(
    user_id     bigint                          not null,
    game_id     bigint                          not null,
    dlc_id      bigint                          null,
    valid       bit         default b'1'        not null,
    user_tag    varchar(45)                     null,
    status      varchar(45) default 'purchased' null,
    purchase_id bigint                          not null,
    constraint fk_users_games_games
        foreign key (game_id) references dev2.game (id),
    constraint fk_users_games_user
        foreign key (user_id) references dev2.users (id),
    constraint users_games_users_purchases_id_fk
        foreign key (purchase_id) references dev2.users_purchases (id)
);

create index fk_users_games_games1_idx
    on dev2.users_games (game_id);

create index fk_users_games_user_history1_idx
    on dev2.users_games (purchase_id);

create index fk_users_games_users1_idx
    on dev2.users_games (user_id);

