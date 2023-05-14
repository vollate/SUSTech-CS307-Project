package com.TheEnd.www.service;

import java.util.ArrayList;

public record QueryRequest(ArrayList content) {
    public QueryRequest() {
        this(null);
    }
}
