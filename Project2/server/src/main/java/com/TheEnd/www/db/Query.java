package com.TheEnd.www.db;

import java.util.ArrayList;

public interface Query {

    public boolean openDataSource();

    public boolean closeDataSource();

    public ArrayList query(QueryType t, ArrayList content);

    public boolean check(QueryType t, ArrayList content);
}