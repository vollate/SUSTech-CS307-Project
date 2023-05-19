package com.TheEnd.www.customer;

import com.TheEnd.www.db.requestTypes.*;
import com.TheEnd.www.service.RequestResponse;
import com.TheEnd.www.service.RequestSolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class Handler {
    @Autowired
    private RequestSolver solver;

    @PostMapping(value = "/request")
    public RequestResponse query(@RequestBody ReqBody body) {
        System.out.println("type: " + body.type());
        System.out.println("content: " + body.content());
        try {
            RequestType requestType = RequestType.valueOf(body.type());
            ArrayList requestContent = body.content();
            String op = (String) requestContent.get(0);
            requestContent.remove(0);
            return new RequestResponse(switch (requestType) {
                case UserOp -> solver.solveUserOp(UserOpType.valueOf(op), requestContent);
                case PostOp -> solver.solvePostOp(PostOpType.valueOf(op), requestContent);
                case SearchOp -> solver.solveSearchOp(SearchOpType.valueOf(op), requestContent);
                case RelationOp -> solver.solveRelationOp(RelationOpType.valueOf(op), requestContent);
                case ShowOp -> solver.solveShowOp(ShowOpType.valueOf(op), requestContent);
                default -> new ArrayList();
            });
        } catch (Exception e) {

            return new RequestResponse();
        }
    }
}
