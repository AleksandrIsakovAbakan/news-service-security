package com.example.newsservicesecurity.service;

import com.example.newsservicesecurity.api.v1.request.NewsCategoryRq;
import com.example.newsservicesecurity.api.v1.response.NewsCategoryRs;
import com.example.newsservicesecurity.entity.NewsCategoryEntity;
import com.example.newsservicesecurity.exception.AlreadySuchNameException;
import com.example.newsservicesecurity.exception.EntityNotFoundException;
import com.example.newsservicesecurity.mappers.NewsCategoryMapper;
import com.example.newsservicesecurity.repository.NewsCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsCategoryService {

    private final NewsCategoryRepository newsCategoryRepository;

    public List<NewsCategoryRs> getAllNewsCategory(UserDetails userDetails, Integer offset, Integer perPage) {

        if (offset == null) offset = 0;
        if (perPage == null) perPage = 10;
        Pageable pageable = PageRequest.of(offset, perPage);

        List<NewsCategoryEntity> content = newsCategoryRepository.findAll(pageable).getContent();
        log.info("getAllNewsCategory: " + userDetails.getUsername() + ", " + offset + ", " + perPage);

        return NewsCategoryMapper.INSTANCE.toDTO(content);
    }

    public NewsCategoryRs getIdNewsCategory(Long id, UserDetails userDetails) {

        NewsCategoryEntity byId = newsCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News category is not found Id=" + id));
        log.info("getIdNewsCategory: " + userDetails.getUsername() + ", " + id);

        return NewsCategoryMapper.INSTANCE.toDTO(byId);
    }

    public NewsCategoryRs putIdNewsCategory(UserDetails userDetails, Long id, NewsCategoryRq newsCategoryRq) {
        newsCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News category is not found Id=" + newsCategoryRq.getId()));

        NewsCategoryEntity newsCategoryEntity = NewsCategoryMapper.INSTANCE.toModel(newsCategoryRq);

        NewsCategoryEntity save = newsCategoryRepository.save(newsCategoryEntity);

        log.info("putIdNewsCategory: " + userDetails.getUsername() + ", " + newsCategoryRq);

        return NewsCategoryMapper.INSTANCE.toDTO(save);
    }

    public NewsCategoryRs addNewsCategory(UserDetails userDetails, NewsCategoryRq newsCategoryRq) {

        NewsCategoryEntity newsCategoryEntity = NewsCategoryMapper.INSTANCE.toModel(newsCategoryRq);

        Optional<NewsCategoryEntity> newsCategoryEntity1 = newsCategoryRepository
                .findByNewsCategory(newsCategoryRq.getNewsCategory());
        if (newsCategoryEntity1.isPresent()) {
            throw new AlreadySuchNameException("There is already such a news category " + newsCategoryRq.getNewsCategory());
        }

        NewsCategoryEntity save = newsCategoryRepository.save(newsCategoryEntity);
        log.info("addNewsCategory: " + userDetails.getUsername() + ", " + newsCategoryRq);

        return NewsCategoryMapper.INSTANCE.toDTO(save);
    }

    public void deleteNewsCategory(Long id, UserDetails userDetails) {

        newsCategoryRepository.deleteById(id);
        log.info("deleteNewsCategory: " + userDetails.getUsername() + ", " + id);
    }

    public NewsCategoryEntity testNewsCategoryById(Long id){

        return newsCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("NewsCategory not found " + id));
    }
}
