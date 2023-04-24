import classes.*;
import database.*;

import com.alibaba.fastjson.JSON;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        List<Post> posts;
        List<Replies> replies;
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

        System.out.println("Start for inserting database, '1' for normal, '2' for batch.");
        Scanner sc = new Scanner(System.in);
        int op = sc.nextInt();
        if (op == 1) {
            System.out.println("Begin normal insert");
            DB.openDB(prop);
            DB.dropAll();
            DB.genTable();
            start = System.currentTimeMillis();
            InsertPost.loadPosts(posts);
            InsertReply.loadReplies(replies);
            end = System.currentTimeMillis();
            DB.closeDB();
            System.out.println("Loaded all data normally: " + (end - start) + "ms");
        } else if (op == 2) {
            System.out.println("Begin batch insert");
            DB.batchFlag = true;
            DB.openDB(prop);
            DB.dropAll();
            DB.genTable();
            start = System.currentTimeMillis();
            InsertPost.loadPostsBatch(posts);
            InsertReply.loadRepliesBatch(replies);
            end = System.currentTimeMillis();
            DB.closeDB();
            System.out.println("Loaded all data with batch: " + (end - start) + "ms");
        } else {
            System.out.println("invalid");
        }


    }
}
