package com.TheEnd.www.service;

import com.TheEnd.www.db.DBOperators;
import com.TheEnd.www.db.requestTypes.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RequestSolver {
    @Autowired
    private DBOperators dbHandler;

    public ArrayList solveUserOp(UserOpType t, ArrayList content) {
        return dbHandler.dealUser(t, content);
    }

    public ArrayList solvePostOp(PostOpType t, ArrayList content) {
        return dbHandler.dealPost(t, content);
    }

    public ArrayList solveSearchOp(SearchOpType t, ArrayList content) {
        return dbHandler.dealSearch(t, content);
    }
    public ArrayList solveRelationOp(RelationOpType t, ArrayList content) {
        return dbHandler.dealRelation(t, content);
    }
    public ArrayList solveShowOp(ShowOpType t, ArrayList content) {
        return dbHandler.dealShow(t, content);
    }
}
