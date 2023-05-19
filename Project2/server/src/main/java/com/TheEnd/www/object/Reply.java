package com.TheEnd.www.object;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;

@Data
@Builder
public class Reply {
    long reply_id;
    String stars;
    String content;
    String author_name;
    ArrayList<SecReply> secReplies = new ArrayList<>();
}
