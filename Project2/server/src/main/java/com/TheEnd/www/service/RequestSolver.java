package com.TheEnd.www.service;

import com.TheEnd.www.db.DBOperators;
import com.TheEnd.www.db.requesttypes.PostOpType;
import com.TheEnd.www.db.requesttypes.SearchOpType;
import com.TheEnd.www.db.requesttypes.UserOpType;
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
}
