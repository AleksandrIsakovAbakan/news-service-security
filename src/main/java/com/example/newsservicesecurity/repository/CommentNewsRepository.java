package com.example.newsservicesecurity.repository;


import com.example.newsservicesecurity.entity.CommentNewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentNewsRepository extends JpaRepository<CommentNewsEntity, Long> {
    List<CommentNewsEntity> findAllByNewsId(long id);

    void deleteAllByNewsId(int id);

}
