package com.TheEnd.www.db;

import com.TheEnd.www.db.requesttypes.PostOpType;
import com.TheEnd.www.db.requesttypes.SearchOpType;
import com.TheEnd.www.db.requesttypes.UserOpType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.sql.Types;
import java.util.ArrayList;

@Component
public class DBImplement implements DBOperators {

    @Autowired
    private JdbcTemplate jdbc;

    private static class SQLSentenses {
        static String UserLogin = "select count(*) from data.users where name = ? and password = ?";
    }

    @Override
    public ArrayList dealUser(UserOpType t, ArrayList content) {
        ArrayList res = new ArrayList();
        switch (t) {
            case Login -> {
                int count = jdbc.queryForObject(SQLSentenses.UserLogin, new Object[]{content.get(0), content.get(1)}, new int[]{Types.VARCHAR, Types.VARCHAR}, int.class);
                res.add(count == 1);
            }
            case CreateUser -> {
                System.out.println("test");
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
        System.out.println("deal post");
        String test = null;
        try {
            test = jdbc.queryForObject("select now()", String.class);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(test);
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
