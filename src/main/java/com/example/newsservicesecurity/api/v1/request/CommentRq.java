package com.example.newsservicesecurity.api.v1.request;

import com.example.newsservicesecurity.entity.NewsEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentRq {

    @NotNull
    @Min(0)
    private long id;

    @NotEmpty
    private String text;

    private LocalDateTime commentTime;

    @NotNull
    @Min(0)
    private int newsId;

    @NotNull
    @Min(0)
    private int userId;

    private NewsEntity newsEntity;
}
