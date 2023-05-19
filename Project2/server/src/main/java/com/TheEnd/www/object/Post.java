package com.TheEnd.www.object;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class Post {
    private long post_id;
    private String title;
    private String posting_time;
    private String author_name;
    private String city;
    private String country;
    private String content;
    ArrayList<Reply> Replies = new ArrayList<>();
}
