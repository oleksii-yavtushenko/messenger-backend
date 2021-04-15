-- TABLE: user
create table "user"
(
    id serial not null constraint user_pk primary key,
    login varchar(64) not null,
    password varchar(64) not null,
    email varchar(64)
);

create unique index user_login_uindex
    on "user" (login);

-- TYPE: status
create type status as ENUM
    (
    'SENT', 'DELAYED',  'EDITED', 'DELETED'
);

-- TABLE: message
create table message
(
    id serial not null
        constraint message_pk
            primary key,
    sender_id int not null constraint message_sender_id_fk references "user" (id),
    recipient_id int constraint message_recipient_id_fk references "user" (id),
    message_text text not null,
    create_time timestamp with TIME ZONE default CURRENT_TIMESTAMP not null,
    modify_time timestamp with TIME ZONE,
    is_read boolean default false,
    status status default 'DELAYED'
);

-- TABLE: online
create table online
(
    user_id int not null
        constraint online_user_id___fk
            references "user" (id),
    last_online_time timestamp with TIME ZONE default CURRENT_TIMESTAMP,
    is_online boolean default false not null
);

create unique index online_user_id_uindex
  on online (user_id);

alter table online
    add constraint online_pk
        primary key (user_id);