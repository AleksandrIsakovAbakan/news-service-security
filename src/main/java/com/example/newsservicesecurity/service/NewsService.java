package com.example.newsservicesecurity.service;

import com.example.newsservicesecurity.api.v1.request.NewsRq;
import com.example.newsservicesecurity.api.v1.response.NewsRsCommentsCount;
import com.example.newsservicesecurity.api.v1.response.NewsRsCommentsList;
import com.example.newsservicesecurity.entity.NewsEntity;
import com.example.newsservicesecurity.entity.User;
import com.example.newsservicesecurity.exception.AccessDeniedException;
import com.example.newsservicesecurity.exception.EntityNotFoundException;
import com.example.newsservicesecurity.mappers.NewsMapperCommentsCount;
import com.example.newsservicesecurity.mappers.NewsMapperCommentsList;
import com.example.newsservicesecurity.repository.CommentNewsRepository;
import com.example.newsservicesecurity.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {

    private final NewsRepository newsRepository;

    private final UserService userService;

    private final NewsCategoryService newsCategoryService;

    private final CommentNewsRepository commentNewsRepository;

    public List<NewsRsCommentsCount> getAllNews(Integer offset, Integer perPage) {

        List<NewsEntity> contentList = new ArrayList<>();
        if (offset == null) offset = 0;
        if (perPage == null) perPage = 10;
        Pageable pageable = PageRequest.of(offset, perPage);
        Page<NewsEntity> content = newsRepository.findAll(pageable);
        if (!content.isEmpty()) {
            contentList = content.getContent();
        }
        log.info("getAllNews: " + offset + ", " + perPage);

        return NewsMapperCommentsCount.INSTANCE.toDTO(contentList);
    }

    public NewsRsCommentsList getIdNews(Long id) {

        NewsEntity byId = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News not found Id=" + id));
        log.info("getIdNews: " + id);

        return NewsMapperCommentsList.INSTANCE.toDTO(byId);
    }

    public NewsRsCommentsList putIdNews(UserDetails userDetails, Long id, NewsRq newsRq) {
        NewsEntity byId = testNews(id);
        User user = userService.findByUsername(userDetails.getUsername());
        newsCategoryService.testNewsCategoryById((long) newsRq.getCategoryId());

        if (user.getId().equals(byId.getUserId())) {
            if (newsRq.getText() != null) byId.setText(newsRq.getText());
            if (newsRq.getCategoryId() != 0) byId.setCategoryId(newsRq.getCategoryId());
            if (newsRq.getUserId() != 0) {

                userService.getUserId((long) newsRq.getUserId());
                byId.setUserId(newsRq.getUserId());
            }

            NewsEntity save = newsRepository.saveAndFlush(byId);
            log.info("putIdNews: " + userDetails.getUsername() + ", " + newsRq);

            return NewsMapperCommentsList.INSTANCE.toDTO(save);
        }
        throw new AccessDeniedException("Only the user who created it can update the news.");
    }

    public NewsRsCommentsList addNews(UserDetails userDetails, NewsRq newsRq) {

        NewsEntity newsEntity = NewsMapperCommentsList.INSTANCE.toModel(newsRq);
        newsEntity.setId(0);
        newsEntity.setNewsTime(LocalDateTime.now());

        newsCategoryService.testNewsCategoryById((long) newsEntity.getCategoryId());

        User user = userService.findByUsername(userDetails.getUsername());
        newsEntity.setUserId(user.getId().intValue());

        NewsEntity save = newsRepository.saveAndFlush(newsEntity);
        log.info("addNews: " + userDetails.getUsername() + ", " + newsRq);

        return NewsMapperCommentsList.INSTANCE.toDTO(save);
    }
    @Transactional
    public void deleteNews(Long id, UserDetails userDetails) {

        NewsEntity byId = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News is not found Id=" + id));
        userService.testAccessUserGetId(byId.getUserId(), userDetails);

        commentNewsRepository.deleteAllByNewsId((int) byId.getId());

        newsRepository.deleteById(id);
        log.info("deleteNews: " + userDetails.getUsername() + ", " + id);
    }

    public NewsEntity testNews(Long id){

        return newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News is not found Id=" + id));
    }

    public List<NewsEntity> getByUserId(Long id) {

        return newsRepository.findAllByUserId(id);
    }

}
