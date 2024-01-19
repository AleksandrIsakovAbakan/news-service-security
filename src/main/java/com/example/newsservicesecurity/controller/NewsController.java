package com.example.newsservicesecurity.controller;

import com.example.newsservicesecurity.api.v1.request.NewsRq;
import com.example.newsservicesecurity.api.v1.response.NewsRsCommentsCount;
import com.example.newsservicesecurity.api.v1.response.NewsRsCommentsList;
import com.example.newsservicesecurity.service.NewsService;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/news")
public class NewsController {

    private final NewsService newsService;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<List<NewsRsCommentsCount>> getPageNews(@RequestParam(required = false) Integer offset,
                                                                 @RequestParam(required = false) Integer perPage) {
        return new ResponseEntity<>(newsService.getAllNews(offset, perPage), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsRsCommentsList> getNewsId(@PathVariable @Min(0) Long id) {
        return new ResponseEntity<>(newsService.getIdNews(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsRsCommentsList> editNews(@AuthenticationPrincipal UserDetails userDetails,
                                                       @PathVariable @Min(0) Long id,
                                                       @Validated @RequestBody(required = false) NewsRq newsRq) {
        return new ResponseEntity<>(newsService.putIdNews(userDetails, id, newsRq), HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsRsCommentsList> addNews(@AuthenticationPrincipal UserDetails userDetails,
                                                      @Validated @RequestBody NewsRq newsRq) {
        return new ResponseEntity<>(newsService.addNews(userDetails, newsRq), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsRsCommentsList> deleteNews(@PathVariable @Min(0) Long id,
                                                         @AuthenticationPrincipal UserDetails userDetails) {
        newsService.deleteNews(id, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
