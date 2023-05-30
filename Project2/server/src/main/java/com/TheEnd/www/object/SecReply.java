package com.TheEnd.www.object;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecReply {
    long secReply_id;
    String stars;
    String content;
    String author_name;
    boolean anonymity;
}
