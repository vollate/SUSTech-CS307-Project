package com.TheEnd.www.db;

import com.TheEnd.www.db.requestTypes.PostOpType;
import com.TheEnd.www.db.requestTypes.SearchOpType;
import com.TheEnd.www.db.requestTypes.UserOpType;

import java.util.ArrayList;

public interface DBOperators {
    public ArrayList dealUser(UserOpType t, ArrayList content);

    public ArrayList dealPost(PostOpType t, ArrayList content);

    public ArrayList dealSearch(SearchOpType t, ArrayList content);

}