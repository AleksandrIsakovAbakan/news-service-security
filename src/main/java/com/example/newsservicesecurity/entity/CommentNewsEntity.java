package com.example.newsservicesecurity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "comments")
public class CommentNewsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String text;

    @Column(name = "comment_time", columnDefinition = "DATE")
    private LocalDateTime commentTime;

    @Column(name = "news_id")
    private int newsId;

    @Column(name = "user_id")
    private int userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="news_id", referencedColumnName = "id", insertable = false, updatable = false)
    private NewsEntity newsEntity;

}
