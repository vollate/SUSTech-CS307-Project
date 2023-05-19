package com.TheEnd.www.object;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    String name;
    String password;
    String user_id;
    String registration_time;
    String phone;
}
