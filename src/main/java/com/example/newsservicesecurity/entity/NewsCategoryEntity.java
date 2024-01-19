package com.example.newsservicesecurity.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "category")
public class NewsCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "news_category", columnDefinition = "VARCHAR(255) NOT NULL")
    private String newsCategory;

    public void setId(int id) {
        this.id = id;
    }

    public void setNewsCategory(String newsCategory) {
        this.newsCategory = newsCategory;
    }
}
