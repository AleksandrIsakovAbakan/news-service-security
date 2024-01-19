package com.example.newsservicesecurity.repository;


import com.example.newsservicesecurity.entity.NewsEntity;
import com.example.newsservicesecurity.model.NewsFilter;
import org.springframework.data.jpa.domain.Specification;

public interface NewsSpecification {

    static Specification<NewsEntity> withFilter(NewsFilter newsFilter){
        return Specification.where(byIdNews(newsFilter.getIdNews()))
                .and(byNameCategory(newsFilter.getNameCategory()))
                .and(byIdCategory(newsFilter.getIdCategory()))
                .and(byNameAuthor(newsFilter.getNameAuthor()))
                .and(byIdAuthor(newsFilter.getIdAuthor()))
                .and(byIdNews(newsFilter.getIdNews()));

    }

    static Specification<NewsEntity> byIdNews(Long idNews) {
        return (root, query, cb) -> {
            if (idNews == null) return null;
            return cb.equal(root.get("id"), idNews);
        };
    }

    static Specification<NewsEntity> byNameCategory(String nameCategory) {
        return (root, query, cb) -> {
            if (nameCategory == null) return null;
            return cb.equal(root.get("newsCategoryEntity").get("newsCategory"), nameCategory);
        };
    }

    static Specification<NewsEntity> byIdCategory(Integer idCategory) {
        return (root, query, cb) -> {
            if (idCategory == null) return null;
            return cb.equal(root.get("newsCategoryEntity").get("id"), idCategory);
        };
    }

    static Specification<NewsEntity> byNameAuthor(String nameAuthor) {
        return (root, query, cb) -> {
            if (nameAuthor == null) return null;
            return cb.equal(root.get("userEntity").get("name"), nameAuthor);
        };
    }

    static Specification<NewsEntity> byIdAuthor(Integer idAuthor) {
        return (root, query, cb) -> {
            if (idAuthor == null) return null;
            return cb.equal(root.get("userEntity").get("id"), idAuthor);
        };
    }

}
