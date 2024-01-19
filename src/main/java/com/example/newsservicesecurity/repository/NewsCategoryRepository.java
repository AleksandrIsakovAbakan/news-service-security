package com.example.newsservicesecurity.repository;


import com.example.newsservicesecurity.entity.NewsCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsCategoryRepository extends JpaRepository<NewsCategoryEntity, Long> {
    Optional<NewsCategoryEntity> findByNewsCategory(String newsCategory);
}
