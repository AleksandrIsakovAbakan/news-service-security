package com.example.newsservicesecurity.service;


import com.example.newsservicesecurity.api.v1.response.NewsRsCommentsCount;
import com.example.newsservicesecurity.entity.NewsEntity;
import com.example.newsservicesecurity.mappers.NewsMapperCommentsCount;
import com.example.newsservicesecurity.model.NewsFilter;
import com.example.newsservicesecurity.repository.NewsRepository;
import com.example.newsservicesecurity.repository.NewsSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SearchService {

    private final NewsRepository newsRepository;

    public List<NewsRsCommentsCount> filterByNews(NewsFilter newsFilter) {

        if (newsFilter.getOffset() == null) newsFilter.setOffset(0);
        if (newsFilter.getPerPage() == null) newsFilter.setPerPage(10);

        List<NewsEntity> content = newsRepository.findAll(NewsSpecification.withFilter(newsFilter),
                PageRequest.of(newsFilter.getOffset(), newsFilter.getPerPage())).getContent();
        if (content.isEmpty()) {
            log.info("getAllNews: " + newsFilter);
            return NewsMapperCommentsCount.INSTANCE.toDTO(new ArrayList<>());
        } else {
            log.info("getAllNews: " + newsFilter);
            return NewsMapperCommentsCount.INSTANCE.toDTO(content);
        }
    }
}
