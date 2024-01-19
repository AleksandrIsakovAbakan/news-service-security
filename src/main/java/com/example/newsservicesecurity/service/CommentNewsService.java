package com.example.newsservicesecurity.service;

import com.example.newsservicesecurity.api.v1.request.CommentRq;
import com.example.newsservicesecurity.api.v1.response.CommentRs;
import com.example.newsservicesecurity.entity.CommentNewsEntity;
import com.example.newsservicesecurity.entity.NewsEntity;
import com.example.newsservicesecurity.entity.User;
import com.example.newsservicesecurity.exception.AccessDeniedException;
import com.example.newsservicesecurity.exception.EntityNotFoundException;
import com.example.newsservicesecurity.mappers.CommentMapper;
import com.example.newsservicesecurity.repository.CommentNewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentNewsService {

    private final CommentNewsRepository commentNewsRepository;

    private final NewsService newsService;

    private final UserService userService;

    public List<CommentRs> getIdNewsComments(Long id) {

        NewsEntity newsEntity = newsService.testNews(id);
        List<CommentNewsEntity> commentNewsEntities = commentNewsRepository.findAllByNewsId(newsEntity.getId());

        if (commentNewsEntities == null) commentNewsEntities = new ArrayList<>();
        log.info("getIdNewsComments: " + id);

        return CommentMapper.INSTANCE.toDTO(commentNewsEntities);
    }

    public CommentRs putIdComment(UserDetails userDetails, Long id, CommentRq commentRq) {

        CommentNewsEntity commentNewsEntity = commentNewsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment is not found Id=" + commentRq.getId()));

        newsService.testNews((long) commentRq.getNewsId());
        User user = userService.getUser(userDetails.getUsername());

        if (user.getId().equals((long) commentNewsEntity.getUserId())) {

            if (commentRq.getText() != null) commentNewsEntity.setText(commentRq.getText());
            if (commentRq.getNewsId() != 0) commentNewsEntity.setNewsId(commentRq.getNewsId());

            CommentNewsEntity save = commentNewsRepository.saveAndFlush(commentNewsEntity);
            log.info("putIdComment: " + userDetails.getUsername() + ", " + commentRq);

            return CommentMapper.INSTANCE.toDTO(save);
        }
        throw new AccessDeniedException("Only the user who created it can update the news.");
    }

    public CommentRs addComment(UserDetails userDetails, CommentRq commentRq) {

        CommentNewsEntity commentNewsEntity = CommentMapper.INSTANCE.toModel(commentRq);

        commentNewsEntity.setId(0);
        commentNewsEntity.setCommentTime(LocalDateTime.now());

        User user = userService.findByUsername(userDetails.getUsername());

        commentNewsEntity.setUserId(user.getId().intValue());
        newsService.testNews((long) commentRq.getNewsId());

        CommentNewsEntity save = commentNewsRepository.saveAndFlush(commentNewsEntity);
        log.info("addComment: " + userDetails.getUsername() + ", " + commentRq);

        return CommentMapper.INSTANCE.toDTO(save);
    }


    public void deleteComment(Long id, UserDetails userDetails) {

        CommentNewsEntity byId = commentNewsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comment is not found Id=" + id));
        userService.testAccessUserGetId((long) byId.getUserId(), userDetails);

        commentNewsRepository.deleteById(id);
        log.info("deleteComment: " + userDetails.getUsername() + ", " + id);
    }

}
