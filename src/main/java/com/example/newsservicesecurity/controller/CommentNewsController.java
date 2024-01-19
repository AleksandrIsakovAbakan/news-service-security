package com.example.newsservicesecurity.controller;

import com.example.newsservicesecurity.api.v1.request.CommentRq;
import com.example.newsservicesecurity.api.v1.response.CommentRs;
import com.example.newsservicesecurity.api.v1.response.NewsRsCommentsList;
import com.example.newsservicesecurity.service.CommentNewsService;
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
@RequestMapping(path = "/api/v1/comment")
public class CommentNewsController {

    private final CommentNewsService commentNewsService;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<List<CommentRs>> getCommentNewsId(@PathVariable Long id) {
        return new ResponseEntity<>(commentNewsService.getIdNewsComments(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentRs> editCommentNews(@AuthenticationPrincipal UserDetails userDetails,
                                                     @PathVariable @Min(0) Long id,
                                                     @Validated @RequestBody CommentRq commentRq) {
        return new ResponseEntity<>(commentNewsService.putIdComment(userDetails, id, commentRq), HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<CommentRs> addCommentNews(@AuthenticationPrincipal UserDetails userDetails,
                                                    @Validated @RequestBody CommentRq commentRq) {
        return new ResponseEntity<>(commentNewsService.addComment(userDetails, commentRq), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN', 'ROLE_MODERATOR')")
    public ResponseEntity<NewsRsCommentsList> deleteCommentNews(@PathVariable @Min(0) Long id,
                                                                @AuthenticationPrincipal UserDetails userDetails) {
        commentNewsService.deleteComment(id, userDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
