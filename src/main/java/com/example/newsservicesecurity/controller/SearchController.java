package com.example.newsservicesecurity.controller;


import com.example.newsservicesecurity.api.v1.response.NewsRsCommentsCount;
import com.example.newsservicesecurity.model.NewsFilter;
import com.example.newsservicesecurity.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<List<NewsRsCommentsCount>> getPageNews(@RequestParam(required = false) String nameCategory,
                                                                 @RequestParam(required = false) Integer idCategory,
                                                                 @RequestParam(required = false) String nameAuthor,
                                                                 @RequestParam(required = false) Integer idAuthor,
                                                                 @RequestParam(required = false) Long idNews,
                                                                 @RequestParam(required = false) Integer offset,
                                                                 @RequestParam(required = false) Integer perPage) {
        return new ResponseEntity<>(searchService.filterByNews(new NewsFilter(nameCategory, idCategory, nameAuthor,
                idAuthor, offset, perPage, idNews)), HttpStatus.OK);
    }
}
