package com.TheEnd.www.service;

import com.TheEnd.www.db.Query;
import com.TheEnd.www.db.QueryType;

import java.util.ArrayList;

public class RequestSolver {
    private static Query dbHandler;

    public static void SetHandler(Query handler) {
        dbHandler = handler;
    }

    public static boolean checkRequest(QueryType t, ArrayList content) {
        return dbHandler.check(t, content);
    }

    public static ArrayList dealRequest(QueryType t, ArrayList content) {
        return dbHandler.query(t, content);
    }
}
