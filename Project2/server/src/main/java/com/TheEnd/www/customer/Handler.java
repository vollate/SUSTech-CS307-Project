package com.TheEnd.www.customer;

import com.TheEnd.www.db.QueryType;
import com.TheEnd.www.service.QueryRequest;
import com.TheEnd.www.service.RequestSolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class Handler {

    @GetMapping(value = "/__query")
    public QueryRequest query(@RequestBody ReqBody body) {
        System.out.println("type: " + body.type());
        System.out.println("content: " + body.content());
        try {
            return new QueryRequest(RequestSolver.dealRequest(QueryType.valueOf(body.type()), body.content()));
        } catch (Exception e) {
            return new QueryRequest();
        }
    }

    @GetMapping(value = "/__login")
    public boolean login(@org.springframework.web.bind.annotation.RequestBody String userName, @org.springframework.web.bind.annotation.RequestBody String password) {
        return false;
    }
}
