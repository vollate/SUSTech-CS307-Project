package com.TheEnd.www;

import com.TheEnd.www.service.RequestSolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
//        RequestSolver.SetHandler(new Query());
        SpringApplication.run(ServerApplication.class, args);
    }

}
