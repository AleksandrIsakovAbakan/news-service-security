package com.example.newsservicesecurity.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsFilter {

    private String nameCategory;

    private Integer idCategory;

    private String nameAuthor;

    private Integer idAuthor;

    private Integer offset;

    private Integer perPage;

    private Long idNews;

    public NewsFilter(String nameCategory, Integer idCategory, String nameAuthor, Integer idAuthor, Integer offset,
                      Integer perPage, Long idNews) {
        this.nameCategory = nameCategory;
        this.idCategory = idCategory;
        this.nameAuthor = nameAuthor;
        this.idAuthor = idAuthor;
        this.offset = offset;
        this.perPage = perPage;
        this.idNews = idNews;
    }
}
