package com.example.newsservicesecurity.api.v1.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class NewsRsCommentsList {

    private long id;

    private String text;

    private LocalDateTime newsTime;

    private int categoryId;

    private int userId;

    private List<CommentRs> commentList;
}
