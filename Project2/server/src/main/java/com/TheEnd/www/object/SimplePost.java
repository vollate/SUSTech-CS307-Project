package com.TheEnd.www.object;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class SimplePost {
    private long post_id;
    private String title;
    private String author_name;
    private String content;
    //This is used for searching return
    // 0 for no appendix, 1 for appendix is form reply, 2 for appendix is from secReply
    private int AppendixType = 0;
    private String AppendixContent;
}

