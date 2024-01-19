package com.example.newsservicesecurity.repository;


import com.example.newsservicesecurity.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface NewsRepository extends JpaRepository<NewsEntity, Long>, JpaSpecificationExecutor<NewsEntity> {

    List<NewsEntity> findAllByUserId(Long userId);

}
