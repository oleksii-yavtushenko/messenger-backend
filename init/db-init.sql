CREATE ROLE postgres WITH LOGIN ENCRYPTED PASSWORD '';

-- CREATE TABLE: user
create table "user"
(
    id serial not null constraint user_pk primary key,
    login varchar(64) not null,
    password varchar(64) not null,
    email varchar(64)
);

create unique index user_login_uindex
    on "user" (login);

-- CREATE TYPE: status
create type status as ENUM
    (
    'SENT', 'DELAYED',  'EDITED', 'DELETED'
);

-- CREATE TABLE: message
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

-- CREATE TABLE: online
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


-- INSERT INTO: user
start transaction;

insert into "user" (id, login, password, email)
values
(1, 'coolboy', 'booy', 'dumb_email@mail.com'),
(2, 'coolgirl', 'giirl', 'normal_mail@mail.com'),
(3, 'admin', 'admin', 'fogmrfog@gmail.com');

commit;

-- INSERT INTO: message
start transaction;

insert into message (id, sender_id, recipient_id, message_text, create_time, modify_time, is_read, status)
values
(1, 1, 2, 'hello, my girl!', '2021-04-17 12:54:30+03'::timestamptz, null, true, 'SENT'),
(2, 2, 1, 'hello, boi, how r u?', '2021-04-17 12:55:02+03'::timestamptz, null, true, 'SENT'),
(3, 1, 2, 'i am fine, thx!', '2021-04-17 12:56:44+03'::timestamptz, '2021-04-17 12:57:32+03', false, 'EDITED');

commit;

-- INSERT INTO: online
start transaction;

insert into online(user_id, last_online_time, is_online)
values (1, '2021-04-17 12:55:04+03'::timestamptz, false),
       (2, '2021-04-17 12:56:56+03'::timestamptz, false);

commit;

