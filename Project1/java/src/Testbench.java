import classes.*;
import database.*;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.*;

public class Testbench {
    public static void main(String[] args) throws SQLException {
        List<Post> posts;
        List<Replies> replies;
        ArrayList<Integer> timeArr = new ArrayList<>();
        int[] batchSizeArr = {10,20,30,100,200,300,1000,2000,3000,10000};
        try {
            String jsonStrPost = Files.readString(Path.of("data/posts.json"));
            String jsonStrReply = Files.readString(Path.of("data/replies.json"));
            posts = JSON.parseArray(jsonStrPost, Post.class);
            replies = JSON.parseArray(jsonStrReply, Replies.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long start, end;
        Properties prop = DB.loadDBUser();
        DB.openDB(prop);


        DB.dropAll();
        DB.genTable();
        start = System.currentTimeMillis();
        InsertPost.loadPosts(posts);
        InsertReply.loadReplies(replies);
        end = System.currentTimeMillis();
        DB.closeDB();
        timeArr.add((int) (end-start));

        DB.batchFlag = true;
        DB.openDB(prop);
        for (int i = 0; i < batchSizeArr.length; i++) {
            DB.BATCH_SIZE = batchSizeArr[i];
            DB.dropAll();
            DB.genTable();
            start = System.currentTimeMillis();
            InsertPost.loadPostsBatch(posts);
            InsertReply.loadRepliesBatch(replies);
            end = System.currentTimeMillis();
            timeArr.add((int) (end-start));
        }
        DB.closeDB();
        for (int i = 0; i < timeArr.size(); i++) {
            if(i==0){
                System.out.println("Normal insert, time = "+timeArr.get(i)+"ms.");
            }else{
                System.out.println("Batch insert, batchSize = "+batchSizeArr[i-1]+", time = "+timeArr.get(i)+"ms.");
            }
        }

    }
}
