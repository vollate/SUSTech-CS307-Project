-- 插入用户/*
INSERT INTO data.users (name, password, user_id, registration_time, phone) VALUES
('user3', 'password1', '003', CURRENT_TIMESTAMP, '1234567890'),
('user4', 'password2', '004', CURRENT_TIMESTAMP, '0987654321');

-- 插入帖子
INSERT INTO data.posts (post_id, title, posting_time, author_name, city, country, content) VALUES
(1, 'Title1', CURRENT_TIMESTAMP, 'user1', 'City1', 'Country1', 'This is the content of post 1.'),
(2, 'Title2', CURRENT_TIMESTAMP, 'user2', 'City2', 'Country2', 'This is the content of post 2.');

-- 插入回复
INSERT INTO data.replies (reply_id, post_id, stars, content, author_name) VALUES
(NEXTVAL('id_sequence.reply_seq'), 1, 5, 'This is a reply.', 'user2'),
(NEXTVAL('id_sequence.reply_seq'), 2, 4, 'This is another reply.', 'user1');

-- 插入次级回复
INSERT INTO data.secondary_replies (secondary_reply_id, reply_id, stars, content, author_name) VALUES
(NEXTVAL('id_sequence.secondary_reply_seq'), 1, 3, 'This is a secondary reply.', 'user1'),
(NEXTVAL('id_sequence.secondary_reply_seq'), 2, 2, 'This is another secondary reply.', 'user2');

-- 插入类别
INSERT INTO data.categories (category, post_id) VALUES
('Category1', 1),
('Category2', 2);

-- 插入关注关系
INSERT INTO relation.follow_relation (followee_name, follower_name) VALUES
('user1', 'user2'),
('user2', 'user1');


-- 插入分享关系
INSERT INTO relation.share_relation (post_id, user_name, time) VALUES
(1, 'user3', CURRENT_TIMESTAMP),
(1, 'user4', CURRENT_TIMESTAMP);

-- 插入喜欢关系
INSERT INTO relation.like_relation (post_id, user_name, time) VALUES
(1, 'user3', CURRENT_TIMESTAMP),
(1, 'user4', CURRENT_TIMESTAMP);

-- 插入收藏关系
INSERT INTO relation.favorite_relation (post_id, user_name, time) VALUES
(1, 'user1', CURRENT_TIMESTAMP),
(2, 'user2', CURRENT_TIMESTAMP);
