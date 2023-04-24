-- schema

create schema data;
create schema relation;
create schema id_sequence;

-- entity

create table data.users
(
    name              varchar primary key,
    user_id           char(18),
    registration_time timestamp,
    phone             char(15)
);

create table data.posts
(
    post_id      numeric primary key,
    title        varchar not null,
    posting_time timestamp,
    author_name  varchar unique,
    city         varchar,
    country      varchar,
    content      text
);

create table data.replies
(
    reply_id    numeric primary key,
    post_id     numeric references data.posts (post_id) not null,
    stars       bigint check ( stars >= 0 ),
    content     text                                    not null check ( content != '' ),
    author_name varchar                                 not null,
    unique (post_id, content, author_name)
);

create table data.secondary_replies
(
    secondary_reply_id numeric primary key,
    reply_id           numeric references data.replies (reply_id) not null,
    stars              bigint check ( stars >= 0 ),
    content            text                                       not null check (content != ''),
    author_name        varchar                                    not null,
    unique (reply_id, content, author_name)
);

create table data.categories
(
    category varchar                                 not null,
    post_id  numeric references data.posts (post_id) not null,
    primary key (category, post_id)
);

-- relation

create table relation.follow_relation
(
    followee_name varchar references data.users (name) not null,
    follower_name varchar                              not null,
    primary key (followee_name, follower_name),
    check ( followee_name != follower_name )
);

create table relation.share_relation
(
    post_id   numeric references data.posts (post_id) not null,
    user_name varchar                                 not null,
    primary key (post_id, user_name)
);

create table relation.favorite_relation
(
    post_id   numeric references data.posts (post_id) not null,
    user_name varchar                                 not null,
    primary key (post_id, user_name)
);

create table relation.like_relation
(
    post_id   numeric references data.posts (post_id) not null,
    user_name varchar                                 not null,
    primary key (post_id, user_name)
);

-- sequence

create sequence id_sequence.reply_seq;
create sequence id_sequence.secondary_reply_seq;

-- rules

create rule user_exist_insert as on insert to data.users
    where exists(select 1
                 from data.users
                 where name = new.name)
    do instead update data.users
               set user_id=new.user_id,
                   registration_time=new.registration_time,
                   phone=new.phone
               where name = new.name;

create rule reply_exist_insert as on insert to data.replies
    where exists(select 1
                 from data.replies
                 where content = new.content
                   and author_name = new.author_name) do instead nothing;

create rule follow_insert as on insert to relation.follow_relation
    where not exists(select 1
                     from data.users
                     where name = new.followee_name)
    do also insert into data.users(name, user_id, registration_time)
            values (new.followee_name, null, current_timestamp(0));

create rule favorite_insert as on insert to relation.favorite_relation
    where not exists(select 1
                     from data.users
                     where name = new.user_name)
    do also insert into data.users(name, user_id, registration_time)
            values (new.user_name, null, current_timestamp(0));

create rule share_insert as on insert to relation.share_relation
    where not exists(select 1
                     from data.users
                     where name = new.user_name)
    do also insert into data.users(name, user_id, registration_time)
            values (new.user_name, null, current_timestamp(0));

create rule like_insert as on insert to relation.like_relation
    where not exists(select 1
                     from data.users
                     where name = new.user_name)
    do also insert into data.users(name, user_id, registration_time)
            values (new.user_name, null, current_timestamp(0));

create rule reply_insert as on insert to data.replies
    where not exists(select 1
                     from data.users
                     where name = new.author_name)
    do also insert into data.users(name, user_id, registration_time)
            values (new.author_name, null, current_timestamp(0));

create rule secondary_reply_insert as on insert to data.secondary_replies
    where not exists(select 1
                     from data.users
                     where name = new.author_name)
    do also insert into data.users(name, user_id, registration_time)
            values (new.author_name, null, current_timestamp(0));