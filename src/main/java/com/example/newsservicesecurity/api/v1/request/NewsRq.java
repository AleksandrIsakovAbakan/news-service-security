package com.example.newsservicesecurity.api.v1.request;

import com.example.newsservicesecurity.entity.CommentNewsEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NewsRq {

    @NotNull
    @Min(0)
    private long id;

    @NotEmpty

    private String text;

    private LocalDateTime newsTime;

    @NotNull
    @Min(0)
    private int categoryId;

    @NotNull
    @Min(0)
    private int userId;

    private List<CommentNewsEntity> commentNewsEntityList;
}
