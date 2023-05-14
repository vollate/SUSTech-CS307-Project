package com.TheEnd.www.customer;

import com.TheEnd.www.service.QueryRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping(value = "/ctl")
    public QueryRequest quee(@RequestParam(value = "type", defaultValue = "test") String type) {
        return new QueryRequest(type, type);
    }
}
