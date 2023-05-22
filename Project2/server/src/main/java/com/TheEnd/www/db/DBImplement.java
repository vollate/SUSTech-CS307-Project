package com.TheEnd.www.db;

import com.TheEnd.www.db.requestTypes.*;
import com.TheEnd.www.object.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


@Component
public class DBImplement implements DBOperators {

    @Autowired
    private JdbcTemplate jdbc;

    private static class SQLSentenses {
        static String UserLogin = "select count(*) from data.users where name = ? and password = ?";
        static String CheckUser = "select count(*) from data.users where name = ?";
        static String InsertUser = "insert into data.users(name, password, user_id, registration_time, phone)" +
                " VALUES (?, ?, ?, current_timestamp, ?)";
        static String DeleteUser = "delete from data.users where name = ?";
        static String ChangePassword = "select * from change_password(?, ?, ?)";
        static String GenNewPostId = "select * from gen_new_postid()";
        static String InsertPost = "insert into data.posts(post_id, title, posting_time, author_name, city, country, content) " +
                "values (?, ?, current_timestamp, ?, ?, ?, ?)";
        static String InsertReply = "insert into data.replies(reply_id, post_id, content, author_name) " +
                "values (nextval('id_sequence.reply_seq')::numeric, ?, ?, ?)";
        static String InsertSecReply = "insert into data.secondary_replies(secondary_reply_id, reply_id, content, author_name) " +
                "values (nextval('id_sequence.secondary_reply_seq')::numeric, ?, ?, ?)";
        static String getPost = "select * from data.posts where post_id = ?";
        static String getReply = "select reply_id, stars, content, author_name from data.replies where post_id = ?";
        static String getSecReply = "select secondary_reply_id, stars, content, author_name from data.secondary_replies where reply_id = ?";
        static String ShowLikePosts = "select p.post_id, p.title, p.author_name, p.content from data.posts p " +
                "join relation.like_relation l on p.post_id = l.post_id where l.user_name = ?";
        static String ShowFavPosts = "select p.post_id, p.title, p.author_name, p.content from data.posts p " +
                "join relation.favorite_relation f on p.post_id = f.post_id where f.user_name = ?";
        static String ShowSharePosts = "select p.post_id, p.title, p.author_name, p.content from data.posts p " +
                "join relation.share_relation s on p.post_id = s.post_id where s.user_name = ?";
        static String ShowFollowers = "select u.name, u.password, u.user_id, u.registration_time, u.phone from data.users u " +
                "join relation.follow_relation f on u.name = f.follower_name where f.followee_name = ?";
        static String ShowFollowees = "select u.name, u.password, u.user_id, u.registration_time, u.phone from data.users u " +
                "join relation.follow_relation f on u.name = f.followee_name where f.follower_name = ?";
        static String ShowUserPost = "select post_id, title, author_name, content from data.posts where author_name = ?";
        static String ShowUserReplyPost = "select p.post_id, p.title, p.author_name, p.content, r.content as reply_content from data.posts p " +
                "join data.replies r on p.post_id = r.post_id where r.author_name = ?";
        static String SearchDefault = "select post_id, title, author_name, content from data.posts where title like ? or content like ? order by posting_time desc limit ? offset ?";
        static String SearchOpt1 = "select p.post_id, p.title, p.author_name, p.content, coalesce(r.content, sr.content) as appendix_content from data.posts p " +
                "left join data.replies r on p.post_id = r.post_id " +
                "left join data.secondary_replies sr on r.reply_id = sr.reply_id " +
                "where p.title like ? or p.content like ? or r.content like ? or sr.content like ? order by p.posting_time desc limit ? offset ?";
        static String SearchOpt2 = "select post_id, title, author_name, content from data.posts where (title like ? or content like ?) and (posting_time between ? and ?) order by posting_time desc limit ? offset ?";
        static String SearchOpt12 = "select p.post_id, p.title, p.author_name, p.content, coalesce(r.content, sr.content) as appendix_content from data.posts p " +
                "left join data.replies r on p.post_id = r.post_id " +
                "left join data.secondary_replies sr on r.reply_id = sr.reply_id " +
                "where (p.title like ? or p.content like ? or r.content like ? or sr.content like ?) and (posting_time between ? and ?) order by p.posting_time desc limit ? offset ?";

    }

    @Override
    public ArrayList dealUser(UserOpType t, ArrayList content) {
        ArrayList res = new ArrayList();
        switch (t) {
            case Login -> {
                int count = jdbc.queryForObject(SQLSentenses.UserLogin,
                        new Object[]{content.get(0), content.get(1)}, new int[]{Types.VARCHAR, Types.VARCHAR}, int.class);
                res.add(count == 1);
            }
            case CreateUser -> {
                int count = jdbc.queryForObject(SQLSentenses.CheckUser,
                        new Object[]{content.get(0)}, new int[]{Types.VARCHAR}, int.class);
                if (count == 0) {
                    jdbc.update(SQLSentenses.InsertUser,
                            new Object[]{content.get(0), content.get(1), content.get(2), content.get(3)},
                            new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
                    res.add(true);
                } else {
                    res.add(false);
                    res.add("User name already exist");
                }
            }
            case DeleteUser -> {
                int count = jdbc.queryForObject(SQLSentenses.CheckUser,
                        new Object[]{content.get(0)}, new int[]{Types.VARCHAR}, int.class);
                if (count == 1) {
                    jdbc.update(SQLSentenses.DeleteUser,
                            new Object[]{content.get(0)},
                            new int[]{Types.VARCHAR});
                    res.add(count == 1);
                } else {
                    res.add(count == 1);
                }
            }
            case ChangePassword -> {
                boolean ret = jdbc.queryForObject(SQLSentenses.ChangePassword,
                        new Object[]{content.get(0), content.get(1), content.get(2)},
                        new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR}, boolean.class);
                res.add(ret);
            }
        }
        System.out.print("return: ");
        System.out.println(res);
        return res;
    }

    @Override
    public ArrayList dealPost(PostOpType t, ArrayList content) {
        ArrayList res = new ArrayList();
        switch (t) {
            case GetPost -> {
                Post post;
                List<Post> postList = jdbc.query(SQLSentenses.getPost, new RowMapper<Post>() {
                            @Override
                            public Post mapRow(ResultSet rs, int rowNum) throws SQLException {
                                return Post.builder()
                                        .post_id(rs.getLong(1))
                                        .title(rs.getString(2))
                                        .posting_time(rs.getString(3))
                                        .author_name(rs.getString(4))
                                        .city(rs.getString(5))
                                        .country(rs.getString(6))
                                        .content(rs.getString(10)).build();
                            }
                        }
                        , content.get(0));
                List<Reply> replyList = jdbc.query(SQLSentenses.getReply, new RowMapper<Reply>() {
                            @Override
                            public Reply mapRow(ResultSet rs, int rowNum) throws SQLException {
                                return Reply.builder()
                                        .reply_id(rs.getLong(1))
                                        .stars(rs.getString(2))
                                        .content(rs.getString(3))
                                        .author_name(rs.getString(4)).build();
                            }
                        }
                        , content.get(0));
                for (int i = 0; i < replyList.size(); i++) {
                    List<SecReply> secReplies = jdbc.query(SQLSentenses.getSecReply, new RowMapper<SecReply>() {
                                @Override
                                public SecReply mapRow(ResultSet rs, int rowNum) throws SQLException {
                                    return SecReply.builder()
                                            .secReply_id(rs.getLong(1))
                                            .stars(rs.getString(2))
                                            .content(rs.getString(3))
                                            .author_name(rs.getString(4)).build();
                                }
                            }
                            , replyList.get(i).getReply_id());
                    replyList.get(i).setSecReplies((ArrayList<SecReply>) secReplies);
                }

                post = postList.get(0);
                post.setReplies((ArrayList<Reply>) replyList);
                res.add(post);
                return res;
            }
            case AddReply -> {
                jdbc.update(SQLSentenses.InsertReply, new Object[]{content.get(0), content.get(1), content.get(2)},
                        new int[]{Types.NUMERIC, Types.VARCHAR, Types.VARCHAR});
            }
            case AddSecondReply -> {
                jdbc.update(SQLSentenses.InsertSecReply, new Object[]{content.get(0), content.get(1), content.get(2)},
                        new int[]{Types.NUMERIC, Types.VARCHAR, Types.VARCHAR});
            }
            case AddPost -> {
                int post_id = jdbc.queryForObject(SQLSentenses.GenNewPostId, int.class);
                jdbc.update(SQLSentenses.InsertPost, new Object[]{post_id, content.get(0), content.get(1), content.get(2), content.get(3), content.get(4)},
                        new int[]{Types.NUMERIC, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
            }
        }
        return res;
    }

    @Override
    public ArrayList dealSearch(SearchOpType t, ArrayList content) {
        System.out.println("deal search");
        ArrayList res = new ArrayList();
        String keyword = "%" + content.get(0) + "%";
        switch (t) {
            case SearchDefault -> {
                int limit = (int) content.get(1);
                int offset = (int) content.get(2);
                res.addAll(jdbc.query(SQLSentenses.SearchDefault, new Object[]{keyword, keyword, limit, offset},
                        (rs, rowNum) -> createSimplePost(rs, 0, null)));
            }

            case SearchOpt1 -> {
                int limit = (int) content.get(1);
                int offset = (int) content.get(2);
                res.addAll(jdbc.query(SQLSentenses.SearchOpt1, new Object[]{keyword, keyword, keyword, keyword, limit, offset},
                        (rs, rowNum) -> createSimplePost(rs, 1, rs.getString("appendix_content"))));
            }

            case SearchOpt2 -> {
                Timestamp timeStart = Timestamp.valueOf(content.get(1).toString());
                Timestamp timeEnd = Timestamp.valueOf(content.get(2).toString());
                int limit = (int) content.get(3);
                int offset = (int) content.get(4);
                res.addAll(jdbc.query(SQLSentenses.SearchOpt2, new Object[]{keyword, keyword, timeStart, timeEnd, limit, offset},
                        (rs, rowNum) -> createSimplePost(rs, 0, null)));
            }

            case SearchOpt12 -> {
                Timestamp timeStart = Timestamp.valueOf(content.get(1).toString());
                Timestamp timeEnd = Timestamp.valueOf(content.get(2).toString());
                int limit = (int) content.get(3);
                int offset = (int) content.get(4);
                res.addAll(jdbc.query(SQLSentenses.SearchOpt12, new Object[]{keyword, keyword, keyword, keyword, timeStart, timeEnd, limit, offset},
                        (rs, rowNum) -> createSimplePost(rs, 1, rs.getString("appendix_content"))));
            }
        }
        return res;
    }

    private SimplePost createSimplePost(ResultSet rs, int appendixType, String appendixContent) throws SQLException {
        return SimplePost.builder()
                .post_id(rs.getLong("post_id"))
                .title(rs.getString("title"))
                .author_name(rs.getString("author_name"))
                .content(rs.getString("content"))
                .AppendixType(appendixType)
                .AppendixContent(appendixContent)
                .build();
    }

    public ArrayList dealRelation(RelationOpType t, ArrayList content) {
        System.out.println("deal relation");
        ArrayList res = new ArrayList();
        boolean noException = true;
        try {

            switch (t) {
                case Like -> {
                    jdbc.update("INSERT INTO relation.like_relation (post_id, user_name, time) VALUES (?, ?, current_timestamp)",
                            new Object[]{content.get(0), content.get(1)},
                            new int[]{Types.NUMERIC, Types.VARCHAR});
                }
                case Fav -> {
                    jdbc.update("INSERT INTO relation.favorite_relation (post_id, user_name, time) VALUES (?, ?, current_timestamp)",
                            new Object[]{content.get(0), content.get(1)},
                            new int[]{Types.NUMERIC, Types.VARCHAR});
                }
                case Share -> {
                    jdbc.update("INSERT INTO relation.share_relation (post_id, user_name, time) VALUES (?, ?, current_timestamp)",
                            new Object[]{content.get(0), content.get(1)},
                            new int[]{Types.NUMERIC, Types.VARCHAR});
                }
                case Follow -> {
                    jdbc.update("INSERT INTO relation.follow_relation (followee_name, follower_name) VALUES (?, ?)",
                            new Object[]{content.get(0), content.get(1)},
                            new int[]{Types.VARCHAR, Types.VARCHAR});

                }
                case DeleteFollow -> {

                    jdbc.update("DELETE FROM relation.follow_relation WHERE followee_name = ? AND follower_name = ?",
                            new Object[]{content.get(0), content.get(1)},
                            new int[]{Types.VARCHAR, Types.VARCHAR});

                }
            }
        }catch (Exception e){
            noException = false;
            res.add(false);
            res.add(e.toString());
        }
        if(noException) res.add(true);
        return res;
    }

    @Override
    public ArrayList dealShow(ShowOpType t, ArrayList content) {
        System.out.println("deal show");
        ArrayList res = new ArrayList();
        String userName = (String) content.get(0);
        switch (t) {
            case ShowLikePosts -> {
                res.addAll(jdbc.query(SQLSentenses.ShowLikePosts, new Object[]{userName}, new RowMapper<SimplePost>() {
                    @Override
                    public SimplePost mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return SimplePost.builder()
                                .post_id(rs.getLong("post_id"))
                                .title(rs.getString("title"))
                                .author_name(rs.getString("author_name"))
                                .content(rs.getString("content"))
                                .build();
                    }
                }));
            }
            case ShowFavPosts -> {
                res.addAll(jdbc.query(SQLSentenses.ShowFavPosts, new Object[]{userName}, (rs, rowNum) -> {
                    return SimplePost.builder()
                            .post_id(rs.getLong("post_id"))
                            .title(rs.getString("title"))
                            .author_name(rs.getString("author_name"))
                            .content(rs.getString("content"))
                            .build();
                }));
            }
            case ShowSharePosts -> {
                res.addAll(jdbc.query(SQLSentenses.ShowSharePosts, new Object[]{userName}, (rs, rowNum) -> {
                    return SimplePost.builder()
                            .post_id(rs.getLong("post_id"))
                            .title(rs.getString("title"))
                            .author_name(rs.getString("author_name"))
                            .content(rs.getString("content"))
                            .build();
                }));
            }
            case ShowFollowers -> {
                res.addAll(jdbc.query(SQLSentenses.ShowFollowers, new Object[]{userName}, (rs, rowNum) -> {
                    return User.builder()
                            .name(rs.getString("name"))
                            .password(rs.getString("password"))
                            .user_id(rs.getString("user_id"))
                            .registration_time(rs.getString("registration_time"))
                            .phone(rs.getString("phone"))
                            .build();
                }));
            }
            case ShowFollowees -> {
                res.addAll(jdbc.query(SQLSentenses.ShowFollowees, new Object[]{userName}, (rs, rowNum) -> {
                    return User.builder()
                            .name(rs.getString("name"))
                            .password(rs.getString("password"))
                            .user_id(rs.getString("user_id"))
                            .registration_time(rs.getString("registration_time"))
                            .phone(rs.getString("phone"))
                            .build();
                }));
            }
            case ShowUserPost -> {
                res.addAll(jdbc.query(SQLSentenses.ShowUserPost, new Object[]{userName}, (rs, rowNum) -> {
                    return SimplePost.builder()
                            .post_id(rs.getLong("post_id"))
                            .title(rs.getString("title"))
                            .author_name(rs.getString("author_name"))
                            .content(rs.getString("content"))
                            .AppendixType(0)
                            .build();
                }));
            }

            case ShowUserReplyPost -> {
                res.addAll(jdbc.query(SQLSentenses.ShowUserReplyPost, new Object[]{userName}, (rs, rowNum) -> {
                    return SimplePost.builder()
                            .post_id(rs.getLong("post_id"))
                            .title(rs.getString("title"))
                            .author_name(rs.getString("author_name"))
                            .content(rs.getString("content"))
                            .AppendixType(1)
                            .AppendixContent(rs.getString("reply_content"))
                            .build();
                }));
            }
            case ShowUserInfo -> {
                res.addAll(jdbc.query("select * from data.users where name = ?", new Object[]{userName}, (rs, rowNum) -> {
                    return User.builder()
                            .name(rs.getString("name"))
                            .user_id(rs.getString("user_id"))
                            .registration_time(rs.getString("registration_time"))
                            .phone(rs.getString("phone"))
                            .build();
                }));
            }
        }
        return res;
    }


}
