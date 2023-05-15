package com.TheEnd.www.service;

import com.TheEnd.www.db.DBOperators;
import com.TheEnd.www.db.requestTypes.PostOpType;
import com.TheEnd.www.db.requestTypes.SearchOpType;
import com.TheEnd.www.db.requestTypes.UserOpType;

import java.util.ArrayList;

public class RequestSolver {
    private static DBOperators dbHandler;

    public static void SetHandler(DBOperators handler) {
        dbHandler = handler;
    }

    public static ArrayList solveUserOp(UserOpType t, ArrayList content) {
        return dbHandler.dealUser(t, content);
    }

    public static ArrayList solvePostOp(PostOpType t, ArrayList content) {
        return dbHandler.dealPost(t, content);
    }

    public static ArrayList solveSearchOp(SearchOpType t, ArrayList content) {
        return dbHandler.dealSearch(t, content);
    }
}
