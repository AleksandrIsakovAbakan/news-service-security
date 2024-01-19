package com.example.newsservicesecurity.api.v1.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentRs {

    private int id;

    private String text;

    private LocalDateTime commentTime;

    private int newsId;

    private int userId;
}
