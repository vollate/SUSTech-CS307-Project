import com.alibaba.fastjson.JSON;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;

public class Main {
    static boolean batchFlag = false;
    static int batchCnt = 0;
    static final int BATCH_SIZE = 1000;
    static Connection con = null;
    static PreparedStatement stmt = null;
    static HashSet<String> replyContentSet = new HashSet<>();    //为使用batch的同时获取自增主键
    static int replyId_java = 0;
    public static void main(String[] args) throws SQLException {
        List<Post> posts;
        List<Replies> replies;
        try {
            String jsonStrPost = Files.readString(Path.of("data/posts.json"));
            String jsonStrReply = Files.readString(Path.of("data/replies.json"));
            posts = JSON.parseArray(jsonStrPost, Post.class);
            replies = JSON.parseArray(jsonStrReply, Replies.class);
            //posts.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Properties prop = loadDBUser();
        openDB(prop);
        dropAll();
        genTable();
        for(Post post: posts){
            loadPosts(post);
            //System.out.println("loading post "+post.getPostID());
        }
        System.out.println("load post compl");
        for(Replies reply: replies){
            loadReplies(reply);
            System.out.println("loading reply "+ reply.getPostID());
        }
        closeDB();


    }

    private static Properties loadDBUser() {
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(new FileInputStream("data/dbUser1.properties")));
            return properties;
        } catch (IOException e) {
            System.err.println("can not find db user file");
            throw new RuntimeException(e);
        }
    }

    private static void loadPosts(Post post){
        String insertUser = "insert into data.users(name, user_id, registration_time, phone) " +
                "values (?, ?, ?, ?);";
        String insertPost = "insert into data.posts(post_id, title, posting_time, author_name, city, country, content)\n" +
                "values (?, ?, ?, ?, ?, ?, ?);";
        String insertFollow = "insert into relation.follow_relation(followee_name, follower_name)\n" +
                "values (?, ?);";
        String insertFav = "insert into relation.favorite_relation(post_id, user_name)\n" +
                "values (?, ?);";
        String insertLike = "insert into relation.like_relation(post_id, user_name)\n" +
                "values (?, ?);";
        String insertShare = "insert into relation.share_relation(post_id, user_name)\n" +
                "values (?, ?);";
        String insertCate = "insert into data.categories(category, post_id)\n" +
                "values (?, ?);";
        String address = post.getPostingCity();
        String[] addressToken = address.split(", ");
        List<String> followers = post.getAuthorFollowedBy();
        List<String> favors = post.getAuthorFavorite();
        List<String> likers = post.getAuthorLiked();
        List<String> sharers = post.getAuthorShared();
        List<String> categories = post.getCategory();
        if (con != null) {
            try {
                stmt = con.prepareStatement(insertUser);
                stmt.setString(1, post.getAuthor());
                stmt.setInt(2, post.getPostID());
                Timestamp timestamp = Timestamp.valueOf(post.getAuthorRegistrationTime());
                stmt.setTimestamp(3, timestamp);
                stmt.setString(4, post.getAuthoPhone());
                if(batchFlag){
                    stmt.addBatch();
                    batchCnt++;
                }else {
                    stmt.execute();
                }

            } catch (SQLException ex) {
                System.out.println("insertUser error");
                throw new RuntimeException(ex);
            }
            try {
                stmt = con.prepareStatement(insertPost);
                stmt.setInt(1, post.getPostID());
                stmt.setString(2, post.getTitle());
                Timestamp timestamp = Timestamp.valueOf(post.getPostingTime());
                stmt.setTimestamp(3, timestamp);
                stmt.setString(4, post.getAuthor());
                stmt.setString(5, addressToken[0]);
                if(addressToken[1]!=null){
                    stmt.setString(6, addressToken[1]);
                }
                stmt.setString(7, post.getContent());
                if(batchFlag){
                    stmt.addBatch();
                    batchCnt++;
                }else {
                    stmt.execute();
                }
            } catch (SQLException ex) {
                System.out.println("insertPost error");
                throw new RuntimeException(ex);
            }
            try {
                stmt = con.prepareStatement(insertFollow);
                for(String follower: followers){
                    stmt.setString(1, post.getAuthor());
                    stmt.setString(2, follower);
                    if(batchFlag){
                        stmt.addBatch();
                        batchCnt++;
                    }else {
                        stmt.execute();
                    }
                }
            } catch (SQLException ex) {
                System.out.println("insertFollowers error");
                throw new RuntimeException(ex);
            }
            try {
                stmt = con.prepareStatement(insertFav);
                for(String favor: favors){
                    stmt.setInt(1, post.getPostID());
                    stmt.setString(2, favor);
                    if(batchFlag){
                        stmt.addBatch();
                        batchCnt++;
                    }else {
                        stmt.execute();
                    }
                }
            } catch (SQLException ex) {
                System.out.println("insertFavors error");
                throw new RuntimeException(ex);
            }
            try {
                stmt = con.prepareStatement(insertLike);
                for(String like: likers){
                    stmt.setInt(1, post.getPostID());
                    stmt.setString(2, like);
                    if(batchFlag){
                        stmt.addBatch();
                        batchCnt++;
                    }else {
                        stmt.execute();
                    }
                }
            } catch (SQLException ex) {
                System.out.println("insertLiker error");
                throw new RuntimeException(ex);
            }
            try {
                stmt = con.prepareStatement(insertShare);
                for(String sharer: sharers){
                    stmt.setInt(1, post.getPostID());
                    stmt.setString(2, sharer);
                    if(batchFlag){
                        stmt.addBatch();
                        batchCnt++;
                    }else {
                        stmt.execute();
                    }
                }
            } catch (SQLException ex) {
                System.out.println("insertSharer error");
                throw new RuntimeException(ex);
            }
            try {
                stmt = con.prepareStatement(insertCate);
                for(String cate: categories){
                    stmt.setString(1, cate);
                    stmt.setInt(2, post.getPostID());
                    if(batchFlag){
                        stmt.addBatch();
                        batchCnt++;
                    }else {
                        stmt.execute();
                    }
                }
            } catch (SQLException ex) {
                System.out.println("insertCategory error");
                throw new RuntimeException(ex);
            }
        }

    }
    private static void loadReplies(Replies reply){
        boolean insertReplyFlag = false;
        if(!replyContentSet.contains(reply.getReplyContent())){
            replyContentSet.add(reply.getReplyContent());
            insertReplyFlag = true;
            replyId_java++;
        }
        String insertReply = "insert into data.replies(reply_id, post_id, stars, content, author_name)\n" +
                "values (nextval('id_sequence.reply_seq')::numeric, ?, ?, ?, ?)";
        String getSeqVal = "SELECT last_value FROM id_sequence.reply_seq;";
        String insertSecReply = "insert into data.secondary_replies(secondary_reply_id, reply_id, stars, content, author_name)\n" +
                "values (nextval('id_sequence.secondary_reply_seq')::numeric, ?, ?, ?, ?);";
        int reply_id = -1;
        if(con!=null){
            if(insertReplyFlag) {
                try {
                    stmt = con.prepareStatement(insertReply);
                    stmt.setInt(1, reply.getPostID());
                    stmt.setInt(2, reply.getReplyStars());
                    stmt.setString(3, reply.getReplyContent());
                    stmt.setString(4, reply.getReplyAuthor());
                    if (batchFlag) {
                        stmt.addBatch();
                        batchCnt++;
                    } else {
                        stmt.execute();
                    }
                } catch (SQLException ex) {
                    System.out.println("insertReply error");
                    throw new RuntimeException(ex);
                }
            }
            if(!batchFlag) {
                try {
                    ResultSet resultSet = con.createStatement().executeQuery(getSeqVal);
                    if (resultSet.next()) {
                        reply_id = resultSet.getInt(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try {
                stmt = con.prepareStatement(insertSecReply);
                if(batchFlag){
                    stmt.setInt(1, replyId_java);
                }else {
                    stmt.setInt(1, reply_id);
                }
                stmt.setInt(2, reply.getSecondaryReplyStars());
                stmt.setString(3, reply.getSecondaryReplyContent());
                stmt.setString(4, reply.getSecondaryReplyAuthor());
                if(batchFlag){
                    stmt.addBatch();
                    batchCnt++;
                }else {
                    stmt.execute();
                }

            } catch (SQLException ex) {
                System.out.println("insertReply error");
                throw new RuntimeException(ex);
            }
        }
    }



    private static void openDB(Properties prop) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (Exception e) {
            System.err.println("Cannot find the Postgres driver. Check CLASSPATH.");
            System.exit(1);
        }
        String url = "jdbc:postgresql://" + prop.getProperty("host") + "/" + prop.getProperty("database");
        try {
            con = DriverManager.getConnection(url, prop);
            if (con != null) {
                System.out.println("Successfully connected to the database "
                        + prop.getProperty("database") + " as " + prop.getProperty("user"));
                //con.setAutoCommit(false);
            }
        } catch (SQLException e) {
            System.err.println("Database connection failed");
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void genTable() {
        String filePath = "SQL/gen-table.sql";
        exeSqlFile(filePath);
    }
    private static void sampleInsert() {
        String filePath = "SQL/sample.sql";
        exeSqlFile(filePath);
    }
    private static void dropAll() {
        String filePath = "SQL/drop-all.sql";
        exeSqlFile(filePath);
    }
    private static void exeSqlFile(String filePath){
        try {
            String sqlFileContent = new String(Files.readAllBytes(Paths.get(filePath)));
            List<String> statements = Arrays.asList(sqlFileContent.split(";"));

            for (String statement : statements) {
                if (!statement.trim().isEmpty()) {
                    //System.out.println("Executing statement: " + statement);
                    try (Statement stmt = con.createStatement()) {
                        stmt.execute(statement);
                    }
                }
            }
            //con.commit();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    private static void closeDB() {
        if (con != null) {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                con.close();
                con = null;
            } catch (Exception ignored) {
            }
        }
    }
}
