import classes.Post;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class InsertPost {
    public static int batchCnt = 0;
    public static PreparedStatement stmt = null;
    public static void setPreparedStatement(String command){
        // Posts
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
        // Replies
        String insertReply = "insert into data.replies(reply_id, post_id, stars, content, author_name)\n" +
                "values (nextval('id_sequence.reply_seq')::numeric, ?, ?, ?, ?)";
        String getSeqVal = "SELECT last_value FROM id_sequence.reply_seq;";
        String insertSecReply = "insert into data.secondary_replies(secondary_reply_id, reply_id, stars, content, author_name)\n" +
                "values (nextval('id_sequence.secondary_reply_seq')::numeric, ?, ?, ?, ?);";
        if(DB.con != null) {

            try {
                switch (command) {
                    case "insertUser": stmt = DB.con.prepareStatement(insertUser); break;
                    case "insertPost": stmt = DB.con.prepareStatement(insertPost); break;
                    case "insertFollow": stmt = DB.con.prepareStatement(insertFollow); break;
                    case "insertFav": stmt = DB.con.prepareStatement(insertFav); break;
                    case "insertLike": stmt = DB.con.prepareStatement(insertLike); break;
                    case "insertShare": stmt = DB.con.prepareStatement(insertShare); break;
                    case "insertCate": stmt = DB.con.prepareStatement(insertCate); break;
                    case "insertReply": stmt = DB.con.prepareStatement(insertReply); break;
                    case "getSeqVal": stmt = DB.con.prepareStatement(getSeqVal); break;
                    case "insertSecReply": stmt = DB.con.prepareStatement(insertSecReply); break;
                    default:
                        System.err.println("command "+command+" is not valid!");
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public static void loadInsertUser(Post post){
        try {
            stmt.setString(1, post.getAuthor());
            stmt.setString(2, post.getAuthorID());
            Timestamp timestamp = Timestamp.valueOf(post.getAuthorRegistrationTime());
            stmt.setTimestamp(3, timestamp);
            stmt.setString(4, post.getAuthoPhone());
            if(DB.batchFlag){
                stmt.addBatch();
                batchCnt++;
            }else {
                stmt.execute();
            }
        }catch (SQLException ex) {
            System.out.println("insertUser error");
            throw new RuntimeException(ex);
        }
    }
    public static void loadInsertPost(Post post){
        String address = post.getPostingCity();
        String[] addressToken = address.split(", ");
        try {
            stmt.setInt(1, post.getPostID());
            stmt.setString(2, post.getTitle());
            Timestamp timestamp = Timestamp.valueOf(post.getPostingTime());
            stmt.setTimestamp(3, timestamp);
            stmt.setString(4, post.getAuthor());
            if(addressToken.length==3){
                stmt.setString(5, addressToken[0]+", "+addressToken[1]);
                stmt.setString(6, addressToken[2]);
            }else {
                stmt.setString(5, addressToken[0]);
                if (addressToken[1] != null) {
                    stmt.setString(6, addressToken[1]);
                }
            }
            stmt.setString(7, post.getContent());
            if(DB.batchFlag){
                stmt.addBatch();
                batchCnt++;
            }else {
                stmt.execute();
            }
        }catch (SQLException ex) {
            System.out.println("insertPost error");
            throw new RuntimeException(ex);
        }
    }
    public static void loadInsertFollow(Post post){
        List<String> followers = post.getAuthorFollowedBy();
        try {
            for(String follower: followers){
                stmt.setString(1, post.getAuthor());
                stmt.setString(2, follower);
                if(DB.batchFlag){
                    stmt.addBatch();
                    batchCnt++;
                }else {
                    stmt.execute();
                }
            }

        }catch (SQLException ex) {
            System.out.println("insertFollow error");
            throw new RuntimeException(ex);
        }
    }
    public static void loadInsertFav(Post post){
        List<String> favors = post.getAuthorFavorite();
        try {
            for(String favor: favors){
                stmt.setInt(1, post.getPostID());
                stmt.setString(2, favor);
                if(DB.batchFlag){
                    stmt.addBatch();
                    batchCnt++;
                }else {
                    stmt.execute();
                }
            }
        }catch (SQLException ex) {
            System.out.println("insertUser error");
            throw new RuntimeException(ex);
        }
    }
    public static void loadInsertLike(Post post){
        List<String> likers = post.getAuthorLiked();
        try {
            for(String like: likers){
                stmt.setInt(1, post.getPostID());
                stmt.setString(2, like);
                if(DB.batchFlag){
                    stmt.addBatch();
                    batchCnt++;
                }else {
                    stmt.execute();
                }
            }
        }catch (SQLException ex) {
            System.out.println("insertUser error");
            throw new RuntimeException(ex);
        }
    }
    public static void loadInsertShare(Post post){
        List<String> sharers = post.getAuthorShared();
        try {
            for(String sharer: sharers){
                stmt.setInt(1, post.getPostID());
                stmt.setString(2, sharer);
                if(DB.batchFlag){
                    stmt.addBatch();
                    batchCnt++;
                }else {
                    stmt.execute();
                }
            }
        }catch (SQLException ex) {
            System.out.println("insertUser error");
            throw new RuntimeException(ex);
        }
    }
    public static void loadInsertCate(Post post){
        List<String> categories = post.getCategory();
        try {
            for(String cate: categories){
                stmt.setString(1, cate);
                stmt.setInt(2, post.getPostID());
                if(DB.batchFlag){
                    stmt.addBatch();
                    batchCnt++;
                }else {
                    stmt.execute();
                }
            }
        }catch (SQLException ex) {
            System.out.println("insertUser error");
            throw new RuntimeException(ex);
        }
    }
    public static void loadPosts(List<Post> posts) throws SQLException {
        for(Post post: posts){
            setPreparedStatement("insertUser");
            loadInsertUser(post);
            setPreparedStatement("insertPost");
            loadInsertPost(post);
            setPreparedStatement("insertFollow");
            loadInsertFollow(post);
            setPreparedStatement("insertFav");
            loadInsertFav(post);
            setPreparedStatement("insertLike");
            loadInsertLike(post);
            setPreparedStatement("insertShare");
            loadInsertShare(post);
            setPreparedStatement("insertCate");
            loadInsertCate(post);
            //System.out.println("load "+ post.getPostID());
        }
    }
    public static void loadPostsBatch(List<Post> posts) throws SQLException {
        setPreparedStatement("insertUser");
        for(Post post: posts){
            loadInsertUser(post);
            if(batchCnt>DB.BATCH_SIZE){
                stmt.executeBatch();
                System.out.println("execute "+batchCnt+" data in batch");
                batchCnt = 0;
                stmt.clearBatch();
            }
        }
        if(batchCnt!=0){
            stmt.executeBatch();
            System.out.println("execute "+batchCnt+" data in batch");
            batchCnt = 0;
            stmt.clearBatch();
        }
        setPreparedStatement("insertPost");
        for(Post post: posts){
            loadInsertPost(post);
            if(batchCnt>DB.BATCH_SIZE){
                stmt.executeBatch();
                System.out.println("execute "+batchCnt+" data in batch");
                batchCnt = 0;
                stmt.clearBatch();
            }
        }
        if(batchCnt!=0){
            stmt.executeBatch();
            System.out.println("execute "+batchCnt+" data in batch");
            batchCnt = 0;
            stmt.clearBatch();
        }
        setPreparedStatement("insertFollow");
        for(Post post: posts){
            loadInsertFollow(post);
            if(batchCnt>DB.BATCH_SIZE){
                stmt.executeBatch();
                System.out.println("execute "+batchCnt+" data in batch");
                batchCnt = 0;
                stmt.clearBatch();
            }
        }
        if(batchCnt!=0){
            stmt.executeBatch();
            System.out.println("execute "+batchCnt+" data in batch");
            batchCnt = 0;
            stmt.clearBatch();
        }
        setPreparedStatement("insertFav");
        for(Post post: posts){
            loadInsertFav(post);
            if(batchCnt>DB.BATCH_SIZE){
                stmt.executeBatch();
                System.out.println("execute "+batchCnt+" data in batch");
                batchCnt = 0;
                stmt.clearBatch();
            }
        }
        if(batchCnt!=0){
            stmt.executeBatch();
            System.out.println("execute "+batchCnt+" data in batch");
            batchCnt = 0;
            stmt.clearBatch();
        }
        setPreparedStatement("insertLike");
        for(Post post: posts){
            loadInsertLike(post);
            if(batchCnt>DB.BATCH_SIZE){
                stmt.executeBatch();
                System.out.println("execute "+batchCnt+" data in batch");
                batchCnt = 0;
                stmt.clearBatch();
            }
        }
        if(batchCnt!=0){
            stmt.executeBatch();
            System.out.println("execute "+batchCnt+" data in batch");
            batchCnt = 0;
            stmt.clearBatch();
        }
        setPreparedStatement("insertShare");
        for(Post post: posts){
            loadInsertShare(post);
            if(batchCnt>DB.BATCH_SIZE){
                stmt.executeBatch();
                System.out.println("execute "+batchCnt+" data in batch");
                batchCnt = 0;
                stmt.clearBatch();
            }
        }
        if(batchCnt!=0){
            stmt.executeBatch();
            System.out.println("execute "+batchCnt+" data in batch");
            batchCnt = 0;
            stmt.clearBatch();
        }
        setPreparedStatement("insertCate");
        for(Post post: posts){
            loadInsertCate(post);
            if(batchCnt>DB.BATCH_SIZE){
                stmt.executeBatch();
                System.out.println("execute "+batchCnt+" data in batch");
                batchCnt = 0;
                stmt.clearBatch();
            }
        }
        if(batchCnt!=0){
            stmt.executeBatch();
            System.out.println("execute "+batchCnt+" data in batch");
            batchCnt = 0;
            stmt.clearBatch();
        }
        DB.con.commit();
    }
}
