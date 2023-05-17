package com.TheEnd.www.db;

import com.TheEnd.www.db.requestTypes.PostOpType;
import com.TheEnd.www.db.requestTypes.SearchOpType;
import com.TheEnd.www.db.requestTypes.UserOpType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class DBImplement implements  DBOperators {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public CompletableFuture<int[]> insertBatchAsync(List<String> examples) {
        String sql = "INSERT INTO example (id, name) VALUES (?, ?)";

        return CompletableFuture.supplyAsync(() -> {
            return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    String example = examples.get(i);
                }

                @Override
                public int getBatchSize() {
                    return examples.size();
                }
            });
        });
    }

//    public static void main(String[] args) {
//        SpringApplication.run(DBImplement.class, args);
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        System.out.println("Hello, this is a test for Spring Boot with JDBC");
//        jdbcTemplate.update("insert into rubbish(name,num) values('fuck',3)");
//    }

    @Override
    public ArrayList dealUser(UserOpType t, ArrayList content) {
        ArrayList res = new ArrayList();
        switch (t) {
            case Login -> {
                return res;
            }
            case CreateUser -> {
            }
            case DeleteUser -> {
            }
            case ChangePassword -> {
            }
        }
        return res;
    }

    @Override
    public ArrayList dealPost(PostOpType t, ArrayList content) {
        ArrayList res = new ArrayList();
        switch (t) {
            case GetPost -> {
                return res;
            }
            case GetReply -> {
            }
            case GetSecondReply -> {
            }
            case AddPost -> {
            }
        }
        return res;
    }

    @Override
    public ArrayList dealSearch(SearchOpType t, ArrayList content) {
        return null;
    }
}
