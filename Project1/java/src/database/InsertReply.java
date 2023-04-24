package database;

import classes.Replies;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;

public class InsertReply {
    public static int batchCnt = 0;
    public static PreparedStatement stmt = null;
    public static void setPreparedStatement(String command){
        // Replies
        String insertReply = "insert into data.replies(reply_id, post_id, stars, content, author_name)\n" +
                "values (nextval('id_sequence.reply_seq')::numeric, ?, ?, ?, ?)";
        String getSeqVal = "SELECT last_value FROM id_sequence.reply_seq;";
        String insertSecReply = "insert into data.secondary_replies(secondary_reply_id, reply_id, stars, content, author_name)\n" +
                "values (nextval('id_sequence.secondary_reply_seq')::numeric, ?, ?, ?, ?);";
        if(DB.con != null) {

            try {
                switch (command) {
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
    public static void loadRepliesBatch(List<Replies> replies){
        HashSet<String> replyContentSet = new HashSet<>();
        int replyId_java = 0;
        boolean insertReplyFlag = false;
        if(DB.con!=null) {
            try {
                Statement stmt0 = DB.con.createStatement();
                for (Replies reply : replies) {
                    if(!replyContentSet.contains(reply.getReplyContent())){
                        replyContentSet.add(reply.getReplyContent());
                        insertReplyFlag = true;
                        replyId_java++;
                    }
                    reply.setReply_id(replyId_java);
                    //System.out.println(reply.toInsertReplySql());
                    //System.out.println(reply.toInsertSecReplySql());
                    stmt0.addBatch(reply.toInsertReplySql());
                    stmt0.addBatch(reply.toInsertSecReplySql());
                    batchCnt += 2;
                    if(batchCnt>DB.BATCH_SIZE){
                        stmt0.executeBatch();
                        System.out.println("execute "+batchCnt+" data in batch");
                        batchCnt = 0;
                        stmt0.clearBatch();
                    }
                }
                if(batchCnt!=0){
                    stmt0.executeBatch();
                    System.out.println("execute "+batchCnt+" data in batch");
                    batchCnt = 0;
                    stmt0.clearBatch();
                }
                DB.con.commit();
            }catch (SQLException ex){
                throw new RuntimeException(ex);
            }
        }
    }
    public static void loadReplies(List<Replies> replies){
        HashSet<String> replyContentSet = new HashSet<>();
        for(Replies reply: replies) {
            boolean insertReplyFlag = false;
            if (!replyContentSet.contains(reply.getReplyContent())) {
                replyContentSet.add(reply.getReplyContent());
                insertReplyFlag = true;
            }
            String getSeqVal = "SELECT last_value FROM id_sequence.reply_seq;";
            int reply_id = -1;
            if (DB.con != null) {
                if (insertReplyFlag) {
                    try {
                        setPreparedStatement("insertReply");
                        stmt.setInt(1, reply.getPostID());
                        stmt.setInt(2, reply.getReplyStars());
                        stmt.setString(3, reply.getReplyContent());
                        stmt.setString(4, reply.getReplyAuthor());
                        if (DB.batchFlag) {
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
                try {
                    ResultSet resultSet = DB.con.createStatement().executeQuery(getSeqVal);
                    if (resultSet.next()) {
                        reply_id = resultSet.getInt(1);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    setPreparedStatement("insertSecReply");
                    stmt.setInt(1, reply_id);
                    stmt.setInt(2, reply.getSecondaryReplyStars());
                    stmt.setString(3, reply.getSecondaryReplyContent());
                    stmt.setString(4, reply.getSecondaryReplyAuthor());
                    stmt.execute();


                } catch (SQLException ex) {
                    System.out.println("insertReply error");
                    throw new RuntimeException(ex);
                }
            }
        }
    }

}
