package com.TheEnd.www.db;

import com.TheEnd.www.db.requestTypes.*;

import java.util.ArrayList;

public interface DBOperators {
    public ArrayList dealUser(UserOpType t, ArrayList content);

    public ArrayList dealPost(PostOpType t, ArrayList content);
    public ArrayList dealSearch(SearchOpType t, ArrayList content);
    public ArrayList dealRelation(RelationOpType t, ArrayList content);
    public ArrayList dealShow(ShowOpType t, ArrayList content);

}