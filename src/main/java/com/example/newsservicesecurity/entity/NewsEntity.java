package com.example.newsservicesecurity.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "news")
public class NewsEntity {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Column(columnDefinition = "VARCHAR(255) NOT NULL")
    private String text;

    @Setter
    @Column(name = "news_time", columnDefinition = "DATE")
    private LocalDateTime newsTime;

    @Setter
    @Column(name = "category_id")
    private int categoryId;

    @Column(name = "user_id")
    private long userId;

    @Setter
    @OneToMany(mappedBy="newsEntity", fetch = FetchType.EAGER)
    private List<CommentNewsEntity> commentNewsEntityList = new ArrayList<>();

    @Setter
    @ManyToOne
    @JoinColumn(name="category_id", nullable=false, insertable=false, updatable=false)
    private NewsCategoryEntity newsCategoryEntity;

    @Setter
    @ManyToOne
    @JoinColumn(name="user_id", nullable=false, insertable=false, updatable=false)
    private User user;

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
