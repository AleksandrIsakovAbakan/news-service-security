package com.example.newsservicesecurity.mappers;


import com.example.newsservicesecurity.api.v1.request.NewsRq;
import com.example.newsservicesecurity.api.v1.response.NewsRsCommentsList;
import com.example.newsservicesecurity.entity.NewsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper()
public interface NewsMapperCommentsList {

    NewsMapperCommentsList INSTANCE = Mappers.getMapper(NewsMapperCommentsList.class);

    List<NewsRsCommentsList> toDTO(List<NewsEntity> list);

    @Mapping(target = "commentList", source = "commentNewsEntityList")
    NewsRsCommentsList toDTO(NewsEntity newsEntity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "newsCategoryEntity", ignore = true)
    NewsEntity toModel(NewsRq newsRq);
}
