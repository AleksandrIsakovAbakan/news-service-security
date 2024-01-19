package com.example.newsservicesecurity.api.v1.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NewsRsCommentsCount {

    private long id;

    private String text;

    private LocalDateTime newsTime;

    private int categoryId;

    private int userId;

    private int commentListCount;
}
