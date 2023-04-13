-- this is a sample to insert to table in schema data by sql
insert into data.users(name, author_id, registration_time, phone)
values ('田所浩二', 114514, '1919-8-10 00:00:01', 10086);

insert into data.posts(post_id, title, posting_time, author, city, country, content)
values (1, '下北泽轶事', '2023-04-13 11:18:12', '昏睡红茶', '下北泽', '霓虹', '仲夏夜之淫梦');

insert into data.replies(reply_id, post_id, stars, content, author_name)
values (nextval('id_sequence.reply_seq')::numeric, 1, 10000, '哼~哼~哼~~啊啊啊啊啊啊啊啊啊啊啊啊', '田所浩二');

insert into data.secondary_replies(secondary_reply_id, reply_id, stars, content, author_name)
values (nextval('id_sequence.secondary_reply_seq')::numeric, 1, 0, '???', 'void');

insert into data.categories(category, post_id)
values ('这么臭的post不要也罢', 1);

-- for relationship
insert into relation.follow_relation(followee_name, follower_name)
values ('田所浩二', '啊对对对');

insert into relation.favorite_relation(post_id, user_name)
values (1, '啊错错错');

insert into relation.like_relation(post_id, user_name)
values (1, '你说的对，但是后面忘了');

insert into relation.share_relation(post_id, user_name)
values (1, '菜就多练，练多就菜');

-- if user already exist, insert will be changed to update automatically
insert into data.users (name, author_id, registration_time, phone) values ('你说的对，但是后面忘了',514,'1984.1.1',075588010114);