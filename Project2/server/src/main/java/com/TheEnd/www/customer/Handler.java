package com.TheEnd.www.customer;

import com.TheEnd.www.db.requestTypes.*;
import com.TheEnd.www.service.RequestSolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

@RestController
public class Handler {

    @GetMapping(value = "/__query")
    public ArrayList query(@RequestBody ReqBody body) {
        System.out.println("type: " + body.type());
        System.out.println("content: " + body.content());
        try {
            RequestType requestType = RequestType.valueOf(body.type());
            ArrayList requestContent = body.content();
            String op = (String) requestContent.get(0);
            requestContent.remove(0);
            return switch (requestType) {
                case UserOp -> RequestSolver.solveUserOp(UserOpType.valueOf(op), requestContent);
                case PostOp -> RequestSolver.solvePostOp(PostOpType.valueOf(op), requestContent);
                case SearchOp -> RequestSolver.solveSearchOp(SearchOpType.valueOf(op), requestContent);
                default -> new ArrayList();
            };
        } catch (Exception e) {

            return new ArrayList();
        }
    }
}
