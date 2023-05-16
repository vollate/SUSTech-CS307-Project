package com.TheEnd.www.service;

import java.util.ArrayList;

public record RequestResponse(ArrayList content) {
    public RequestResponse() {
        this(null);
    }
}
